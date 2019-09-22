package com.audhut.j8ex.objects;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by avdhut on 26/8/18.
 */
public class Shop {


    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Future<Double> getPriceAsynch(String product) {

        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        new Thread(() -> {
            //run the long running task
            try {
                double price = calculatePrice(product);
                //use complete method to set the value
                futurePrice.complete(price);
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        return futurePrice;

    }

    //demonstrates how exception thrown by long running task is propagated to caller
    public Future<Double> getPriceAsynchException(String product) {

        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        new Thread(() -> {
            // the long running task throws exception
            try {
                throw new RuntimeException("error in long running task");
            } catch (Exception ex) {
                futurePrice.completeExceptionally(ex); //complete the CompletableFuture by completing the exception
            }
        }).start();
        return futurePrice;

    }

    //CompletableFuture provides you with convenient methods for which you do not have to spawn a Thread
    //internally it uses the ForkJoinPool.commonPool
    //other option is to give an executor - see other examples for this
    public Future<Double> getPriceAsynch2(String product) {
        //this method takes a Supplier that is run in a different thread - by a Executor using ForkJoinPool and assigned to the Future
        // another variation of this method takes a Supplier and a Executor
        //these are factory methods to create CompletableFuture objects
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public double calculatePrice(String product) {
        //introduce a delay of 1 sec
        delay();
        Random rm = new Random();
        //generate a random price
        return rm.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public String calculatePriceWithCode(String product) {

        double price = calculatePrice(product);
        Random rm = new Random();
        //generate a random discount value
        Discount.Code code = Discount.Code.values()[rm.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", product, price, code);
    }

    public double calculatePriceDelay(String product) {
        //introduce a delay of 1 sec
        delay();
        Random rm = new Random();
        //generate a random price
        return rm.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //this method introduces random delay
    public void randomDelay() {
        Random rm = new Random();
        int delay = 500 + rm.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public float exchangeRate(String currency1, String currency2) {
        return 0.4F;
    }

    /*
    This method uses first stream to get the shops and use the convenient method of CompletableFuture.
    It then creates a second stream using the list of CompletableFuture and returns the final list
    The reason the second list if used instead of adding another map function to the first list is that
    the map function would result in sequential processing of Futures. The first map would produce a Future
    and then it would wait till the join(i.e get ) method of future is returned and then process the next shop
    To prevent the sequential processing, the Future list is first obtained and then processed
     */
    public List<String> findPricesFromShops(List<Shop> shopLst, String product) {

        List<CompletableFuture<String>> priceFutures = shopLst.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f",
                                shop.getName(),
                                shop.calculatePrice(product))
                ))
                .collect(Collectors.toList());

        List<String> priceLst = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return priceLst;
    }

    /*
    This method accepts an executor for a CompletableFuture
    It performs better than parallel streams
     */

    public List<String> findPricesFromShopsExecutor(List<Shop> shopLst, String product, Executor exe) {

        List<CompletableFuture<String>> priceFutures = shopLst.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f",
                                shop.getName(),
                                shop.calculatePrice(product), exe)
                ))
                .collect(Collectors.toList());

        List<String> priceLst = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return priceLst;
    }

    /*
    Pipelining two aynch processes
    Here the output of one async process is the input to the next. The data of the first Future is sent to the next asynch process
    For callback, the thenApply method is used which takes a Function functional interface
     */
    public List<String> findPricesWithPipeline(List<Shop> shopLst, String product, Executor exe) {

        List<CompletableFuture<String>> priceFutures = shopLst.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.calculatePriceWithCode(product), exe))
                .map(future -> future.thenApply(Quote::parse)) //converts future of one type, i.e String, to another, i.e Quote object
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync( //thenCompose is used to call second asynch process
                        () -> Discount.applyDiscount(quote), exe
                )))
                .collect(Collectors.toList());

        List<String> priceLst = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return priceLst;
    }

    /*
    Pipelining or combining two independent processes
     Here the first task finds the price and the next task finds the exchange rate as the shops return the price in say $
     These two tasks are independent. Hence you can use the thenCombine method to process the results of the two tasks
     The second function can be an independent class/service. In this example, method in the same class is used
     */
    public CompletableFuture<Double> findPricesWithCombine(Shop shop, String product, Executor exe) {

        CompletableFuture<Double> priceInUSD =
                CompletableFuture.supplyAsync(
                        () -> shop.calculatePrice(product), exe)
                        .thenCombine(CompletableFuture.supplyAsync( //thenCombine is used to call second independent asynch process
                                () -> shop.exchangeRate("euro", "dollar"), exe), (price, rate) -> price * rate); //takes a biFunction where the second arg is a function to combine the two outputs
        return priceInUSD;
    }


    public Stream<CompletableFuture<String>> findPricesStream(List<Shop> shopLst, String product, Executor exe) {

        return shopLst.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.calculatePriceWithCode(product), exe))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(
                        () -> Discount.applyDiscount(quote), exe
                )));
    }

    /*
    Here the method to call after completion is specified in thenAccept
    At the end, the allOf method is called to wait for all tasks to complete
     */

    public void findPricesNotWaitCompletion(List<Shop> shopLst, String product, Executor exe) {

        long start = System.nanoTime();

        CompletableFuture[] futures = (CompletableFuture[]) findPricesStream(shopLst, product, exe)
                .map(future -> future.thenAccept(   //accepts a Consumer
                        s -> System.out.println(s + " (done in " +
                                ((System.nanoTime() - start) / 1_000_000) + "msecs)"
                        )))
                .toArray(size -> new CompletableFuture[size]);

        CompletableFuture.allOf(futures).join();
        System.out.println("all shops have responded in " +
                ((System.nanoTime() - start) / 1_000_000) + "msecs)");

    }


    /*
    This example uses anyOf method to get a result of one of the task. Any task that completes first is returned
     All tasks should return the same result
     */
    public void findPricesAnyCompletion(List<Shop> shopLst, String product,Executor exe){

        long start = System.nanoTime();

        CompletableFuture[] futures = (CompletableFuture[])findPricesStream(shopLst,product,exe)
                .toArray(size -> new CompletableFuture[size]);

        //whenComplete method can also be used to execute a code after completion
        //It can also inspect a exeception that is thrown by the task
        /*CompletableFuture<Object> anyof = CompletableFuture.anyOf(futures).whenComplete((str, th) -> {
                                                                                             if (th==null) {
                                                                                                 System.out.println("anyOf result is " + str);
                                                                                             }});*/
      CompletableFuture<Object> anyof = CompletableFuture.anyOf(futures);

          String val = (String)anyof.join();

        System.out.println("anyOf value obtained is " + val);

        System.out.println("any of shops has responded in " +
                ((System.nanoTime()-start) / 1_000_000) + "msecs)");



    }



}
