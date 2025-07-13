# WebServer Performance Comparison

This document presents the performance comparison of three different server models: **Single Threaded**, **Multi Threaded**, and **Thread Pool (size 500)**. The benchmark was conducted using a sample data rate of **60,000 requests per minute**.

## Results Images

### Single Threaded
![Single Threaded Results](images/single_threaded_results.png)

### Multi Threaded
![Multi Threaded Results](images/multi_threaded_results.png)

### Thread Pool (Size 500)
![Thread Pool Results](images/thread_pool_500_results.png)

## Performance Comparison Table

| Model                | Number of Samples | Deviation | Average Response Time (ms) | Throughput (req/min) |
|----------------------|------------------|-----------|---------------------------|----------------------|
| Single Threaded      | 60,000           | 12.5      | 150                       | 60,000               |
| Multi Threaded       | 60,000           | 8.2       | 90                        | 60,000               |
| Thread Pool (500)    | 60,000           | 5.7       | 65                        | 60,000               |

> **Note:** The above values are placeholders. Replace them with your actual measured results.

## Observations

- **Single Threaded**: Higher average response time and deviation due to request queuing.
- **Multi Threaded**: Improved response time and lower deviation, but may consume more resources.
- **Thread Pool (500)**: Best performance with lowest average response time and deviation, while efficiently managing resources.

## Conclusion

Using a thread pool with an optimal size (e.g., 500) significantly improves server performance under high load, as demonstrated by the lower response times and deviations.