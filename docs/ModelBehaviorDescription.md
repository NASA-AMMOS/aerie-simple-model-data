# Data Model Behavior Description

## Bucket Rate Limits

Buckets are limited to a maximum volume. If this volume is reached, higher priority buckets will continue to
receive data at the specified rate while lower priority buckets will lose data volume to compensate.
The data rates are computed from the desired data rates with the following relationship:

Line 1
$$\text{If } \sum_{j \in C(P(i))} v_j < u_{P(i)}, \text{ then } u_i := u_{P(i)}. \text{ Otherwise, }$$
Line 2
$$\text{Let } C := \forall {j \in C(P(i)), j > i}\,:\,v_j \leq 0 \text{ be true when this is the bucket to steal from. }$$
Line 3
$$\text{Let } E := u'_{P(i)} - \sum_{j \in C(P(i))} \min\left( \hat{v}'_j, 0 \right) \text{ be the available write rate.}$$
Line 4
$$\text{Let } F := \sum_{j \in C(P(i)),\ j < i} \max\left( \hat{v}'_j, 0 \right) \text{ be the write rate used by higher-priority buckets.}$$
Line 5
$$\text{If } C, u_i := \max\left(0, v_i + \int \min\left( \hat{v}'_i, E - F \right) \right) \text{ to let higher-priority buckets steal from us.}$$
Line 6
$$\text{Otherwise, } u_i := u_{P(i)} \text{ because we can steal if needed.}$$
