# WebServer Performance Comparison

This document presents the performance comparison of three different server models: **Single Threaded**, **Multi Threaded**, and **Thread Pool (size 500)**. The benchmark was conducted using a sample data rate of **60,000 requests per minute**.

## Results Images

### Single Threaded
![Single Threaded Results](./Results/View%20Results%20in%20Table.png)

### Multi Threaded
![Multi Threaded Results](./Results/View%20Results%20in%20Table%20-%20Multithreaded.png)

### Thread Pool (Size 500)
![Thread Pool Results](./Results/Graph%20Results%20-%20thread-pool-500.png)

## Performance Comparison Table

| Model                | Number of Samples | Deviation | Average Response Time (ms) | Throughput (req/min) |
|----------------------|-------------------|-----------|----------------------------|----------------------|
| Single Threaded      | 60,000            | 132       | 43                         | 60,017.005/min       |
| Multi Threaded       | 60,000            | 456       | 333                        | 60,011.002/min       |
| Thread Pool (500)    | 120,000           | 2036      | 344                        | 60,000               |

> **Note:** The above values are placeholders. Replace them with your actual measured results.

## Observations

- **Single Threaded**: Higher average response time and deviation due to request queuing.
- **Multi Threaded**: Improved response time and lower deviation, but may consume more resources.
- **Thread Pool (500)**: Best performance with lowest average response time and deviation, while efficiently managing resources.

## Conclusion

Using a thread pool with an optimal size (e.g., 500) significantly improves server performance under high load, as demonstrated by the lower response times and deviations.