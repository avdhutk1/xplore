package com.audhut.j8ex;

/**
 * Created by avdhut on 21/1/18.
 */
public class ParallelStreamEx {

    /* A parallel stream is a stream that splits its element in multiple chunks, processing each chunk
    in a different thread. Internally it uses a Fork/Join pool with default number of threads = Runtime.getRuntime().getAvailableProcessors
    You can configure it by setting the 'java.util.concurrent.ForkJoinpool.common.parallelism' system property
    Not all solutions can be parallelized and one should measure the performance. Some functions and some data
    structures are good but some are not. For eg.
    If some algorithms mutate shared state. An accumulator is written such that it mutates shared state.
    Similarly auto-boxing and unboxing also hinders performance.
    Some operations like limit, findFirst are better for sequential streams. findAny is better
    If there are 'n'elements and each take 'q' processing time, then 'n*q' is the total time and parallelism
    is good if 'q' is high.
    Parallelism friendliness of data structures like ArrayList,hashSet, treeSet is good.  Same with specialized classes
    like IntStream.
    But functions like iterate are not good
    Not all parallelism is good as it involves overhead of allocating threads.
    Parallel stream use the fork/join pool under the hood
    In fork/join pool one should consider this:
    never call invoke for a sub-task. Use fork or compute. Invoke is only called at the start
    The calling thread can be used after splitting for one of the splits as it better utilizes the thread
     Never call join before the first task is computed as calling join results in the calling thread waiting
     */

    public static void main(String[] args) {


    }
}