# Data Model Behavior Description

## Bucket Rate Limits

Buckets are limited to a maximum volume. If this volume is reached, higher priority buckets will continue to
receive data at the specified rate while lower priority buckets will lose data volume to compensate.
The data rates are computed from the desired data rates with the following relationship:

$$\text{If } \sum_{j \in C(P(i))} v_j < u_{P(i)}, \text{ then } u_i := u_{P(i)}. \text{ Otherwise, }$$
$$\text{Let } C_i := \forall {j \in C(P(i)), j > i}\ :\ v_j \leq 0 \text{ be true when this is the bucket to steal from. }$$
$$\text{Let } E := u_{P(i)}' - \sum_{j \in C(P(i))} \min\left( d_j, 0 \right) \text{ be the available write rate.}$$
$$\text{Let } F := \sum_{j \in C(P(i)),\ j < i} \max\left( d_j, 0 \right) \text{ be the write rate used by higher-priority buckets.}$$
$$\text{If } C, u_i := \max\left(0, v_i + \int \min\left( d_i, E - F \right) \right) \text{ to let higher-priority buckets steal from us.}$$
$$\text{Otherwise, } u_i := u_{P(i)} \text{ because we can steal if needed.}$$ \ 

where \
$C$ is the child bucket \
$P$ is the parent bucket, where $P(i)$ is the parent of child i and $C(P(i))$ si the set of children of the parent of child i \
$d$ is the desired rate \
$v$ is the actual rate \

TODO: include what u and u' are
