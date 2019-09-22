package com.audhut.j8ex;

import com.audhut.j8ex.objects.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by avdhut on 26/8/18.
 */
public class CompletableFutureEx {

    /*
    To support concurrency java5 introduced a lot of libraries/tools, one of which was Future.
    Future models a reference to a result of a asynch operation. The caller thread calls the async operation
    and gets a Future object. It then can continue to do other tasks and get back to the original asyn task
    by calling get to obtain the result
    get blocks till you get the result but it is always better to use the overloaded method get with timeout value
    Also Future has methods like isDone.
    But what happens in case of :
    1. exception of the asynch task. how is the caller notified or is aware. It will block indefinitely without
    knowing that an error occurred.
    2. what if you want to pipe async operation. result of one to pass to another
    3. what if instead of 'get', we want to be notified of task completion
    4. Waiting for completion of all tasks in a set of Future
    5. Waiting for the completion of the quickest task in a set of Future
     All this is supported in Completable future
     */

    final Logger logger = LoggerFactory.getLogger(CompletableFutureEx.class);

    public static void main(String[] args) {

        CompletableFutureEx cfex = new CompletableFutureEx();
        //this method demonstrates how a asyn method is called and Future used
        //see the method comments and comments in Shop class
        cfex.exeComplFuture();

    }

    public void exeComplFuture() {

        Shop firstshop = new Shop("reliance");
        long start = System.nanoTime();
        //normal basic implementation of CompletableFuture. See the Shop class for details
        //Future<Double> futPrice = firstshop.getPriceAsynch("pickle");
        //uncomment the below line to show how error is propagated from long running task
        //Future<Double> futPrice = firstshop.getPriceAsynchException("pickle");

        //example of a concise CompletableFuture method
        //see the method in Shop. uncomment to see the response
        Future<Double> futPrice = firstshop.getPriceAsynch2("pickle2");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        logger.debug("aynch invocation returned after {} millis ", invocationTime);

        //do something else - here we can do other tasks as the asynch method has returned

        double price = 0;
        //once the other tasks are done, go back to the async one to get the result
        try {
            //get blocks . Ideally should not be used if it blocks indefinately. Use overloaded method with timeout
            price = futPrice.get();
        } catch (Exception e) {
            //demonstrates how exception from asynch can be propagated to caller
            //uncomment the above code line to get this exception
            throw new RuntimeException(e);
        }
        logger.debug("value of price returned from asynch operation is {}", price);
        long finalTime = (System.nanoTime() - start) / 1_000_000;
        logger.debug("asynch method returned result after {} millis", finalTime);

        /*
        suppose the Shop API was implementing a asynch operation and you needed to find prices of a product
         from different shops and compare them. How can you implement such a solution.
         One way is to use streams - this is sequential, i.e it will find the price from each shop sequentially
         Another way is to use parallel streams
         Another way is to also use CompletableFuture and once you get the list of CompletableFuture, you can use another
         stream to retrieve results
         */
        List<Shop> shopLst = Arrays.asList(new Shop("reliance"),
                new Shop("wallmart"),
                new Shop("dmart"),
                new Shop("bigbazzar"));
        long asyncApiStart = System.nanoTime();

        /*Here we call the asynch api. It uses streams and CompletableFuture convenient method
         to return a list of CompletableFutures.
         The list of CompletableFuture is again sent to a different stream and using the join method,
         the final result is returned.
         The join method of CompletableFuture is same as Get, except that is does not throw checked exception.
         Hence you do not need to wrap it in a try catch block
         See the comments in Shop on the method for why two streams are required.
         */
        List<String> priceLst = firstshop.findPricesFromShops(shopLst, "pickle");
        logger.debug("prices returned by different shops is {} ", priceLst);
        long finalasynTime = (System.nanoTime() - asyncApiStart) / 1_000_000;
        logger.debug("time taken by shop list is {} ", finalasynTime);

        /*
        One advantage of CompletableFuture over parallel streams is that you can specify a Executor that is
        tuned to your application
        In the below method, an Executor is used that is tuned to 4 threads but is based on the optimum threads required
        The optimum thread is given by the formula t = Ncpu * Ucpu * (1+W/C) - where Ncpu = number of cpu as given by
         Runtime.getRuntime().getAvailableProcessor(). Ucpu = target cpu utilization - usually between 0 and 1.
         you want to target 100%
         W/C = ratio of wait time to compute time - usually 99% almost 100%. Meaning asyn process takes 99% of the time
         This gives a better result that parallel streams on large number of Shops,i.e tasks
         */

        //here a executor is created with min thread of size of shop list with a max cap of 100
        Executor priceExe = Executors.newFixedThreadPool(Math.min(shopLst.size(), 100),
                                                new ThreadFactory(){
                                                    public Thread newThread(Runnable r){
                                                        Thread t = new Thread(r);
                                                        t.setDaemon(true);
                                                        return t;
                                                    }
                                                }
                                                );

        asyncApiStart = System.nanoTime();
        priceLst = firstshop.findPricesFromShopsExecutor(shopLst, "pickle", priceExe);
        logger.debug("prices returned by different shops with executor is {} ", priceLst);
        finalasynTime = (System.nanoTime() - asyncApiStart) / 1_000_000;
        logger.debug("time taken by shop list with executor is {} ", finalasynTime);

        /*
        Pipelining Async tasks & Callback
         Suppose you have two or more async tasks such that result of first is sent to the next
         In such cases, CompletableFuture provides a method called thenCompose which is applied on the first future
         This method takes the result of the first future and passes it to the function called by second CompletableFuture
         There is another method called thenComposeAsynch. The difference is that the thenCompose method executes in the
         same thread as the first future. In thenComposeAsynch, the first future returns the thread to the pool and another
          thread is assigned for the next task. Here it makes not much difference
          For callback,  there is a method called thenApply that accepts a Function functional interface. There are also
            methods thenAccept and thenRun that accepts Consumer.
         */
        asyncApiStart = System.nanoTime();
        priceLst = firstshop.findPricesWithPipeline(shopLst, "pickle", priceExe);
        logger.debug("prices returned by pipeline process is {} ", priceLst);
        finalasynTime = (System.nanoTime() - asyncApiStart) / 1_000_000;
        logger.debug("time taken by pipeline process is {} ", finalasynTime);

        /*
        Pipelining two independent tasks
        Above, we saw how to combine two tasks that were dependent on each other. What if the two tasks were independent and
        we still wanted then to execute asynchronously and combine the results later. i.e two asynch tasks generate results
        independently and then the results are combined
        thenCombine method is used to perform and combine two independent task. There is also a thenCombineAsync method where
         the thread is returned to the pool after the first task
         The then combine takes a BiFunction as a second argument - where the inputs are the results of the two operations
         and the result a combination of those
         */
        asyncApiStart = System.nanoTime();
        Future<Double> finalprice = firstshop.findPricesWithCombine(firstshop, "pickle", priceExe); //not done anything with return value
        finalasynTime = (System.nanoTime() - asyncApiStart) / 1_000_000;
        logger.debug("time taken by combine process is {} ", finalasynTime);

        /* Reacting to a completion of CompletableFuture
         Sometimes you would want to do something after the asynch task is completed. Also wait for all the asynch tasks to complete
         before moving to the next code.
         This can be achieved by 'thenAccept'method which accepts a Consumer. 'WhenComplete' is also another method.
         You can wait for all asyn tasks to complete with the allOf static method. The allOf method returns Void, so you can just wait for all
         tasks to complete and cannot obtain the value
         */
        firstshop.findPricesNotWaitCompletion(shopLst, "pickle", priceExe);
        /*
        The anyOf method can be used if you want only one result from a set of tasks. All task should return the same result
         */
        firstshop.findPricesAnyCompletion(shopLst, "pickle", priceExe);


    }
}