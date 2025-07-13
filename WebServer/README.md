# Web Server Performance Benchmarking

This project benchmarks and compares the performance of three different server models under high load conditions using **Apache JMeter**. The goal is to evaluate how each architecture performs in terms of response time, throughput, and resource efficiency.

---

## 📌 Server Models Tested

1. **Single Threaded Server**
2. **Multi Threaded Server**
3. **Thread Pool Server (Pool Size: 500)**

---

## ⚙️ Load Testing Tool

- **Apache JMeter**
  - Used to simulate a load of **60,000 requests per minute**
  - Measured key metrics: Average Response Time, Throughput, Deviation

---

## 📈 Results Summary

| Server Model         | Samples  | Deviation | Avg. Response Time (ms) | Throughput (req/min) |
|----------------------|----------|-----------|--------------------------|----------------------|
| Single Threaded      | 60,000   | 132       | 43                       | 60,017.005           |
| Multi Threaded       | 60,000   | 456       | 333                      | 60,011.002           |
| Thread Pool (500)    | 120,000  | 2036      | 344                      | 60,000               |

---

## 🖼️ Visual Results

### Single Threaded
![Single Threaded](./Results/View%20Results%20in%20Table.png)

### Multi Threaded
![Multi Threaded](./Results/View%20Results%20in%20Table%20-%20Multithreaded.png)

### Thread Pool (500)
![Thread Pool](./Results/View%20Results%20in%20Table%20-%20thread-pool-500.png)

---

## 🧐 Observations

- **Single Threaded**: Minimal resource usage but queues up requests, leading to lower performance under load.
- **Multi Threaded**: Improves concurrency, but can become inefficient with large thread counts.
- **Thread Pool (500)**: Offers the best balance with controlled resource usage and consistent throughput.

---

## ✅ Conclusion

Using a **Thread Pool** architecture significantly improves server performance under high-load scenarios. It maintains steady throughput and reduces average response time, making it the most scalable and efficient approach among the three.

---

## 📂 Project Structure

```

.
├── Results/
│   ├── View Results in Table.png
│   ├── View Results in Table - Multithreaded.png
│   ├── Graph Results - thread-pool-500.png
|   └── Graph Results.jmx
├── MultiThreaded
│   ├── Server.java
│   └── Client.java
├── SingleThreaded
│   ├── Server.java
│   └── Client.java
├── ThreadPool
│   └── Server.java
└── README.md

````

---

## 🚀 How to Run

1. Clone the repo:
   ```bash
   git clone https://github.com/rishisrivastava07/Java-Full-Stack.git
````

2. Compile and run any server:

   ```bash
   cd WebServer
   javac Server.java
   java Server
   ```

3. Open Apache JMeter and load the test plan (`test-plan.jmx`).

4. Run the test and observe the results.

---

## 🧪 Requirements

* Java 8+
* Apache JMeter 5.3.6
* System with sufficient resources to simulate load

