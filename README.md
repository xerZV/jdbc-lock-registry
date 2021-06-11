# Getting Started

PoC for prevention of overlapping the execution of same scheduled method by two instances of the app using `spring-integration-jdbc`.

Use-case: you have more than one instance of the app running on multiple nodes/pools.

e.g.:

                  [GLB]
                  /   \
              (POOL1)(POOL2)
              /            \
            [LB]          [LB]
            /  \          /  \
        {p1.i1}{p1.i2}{p2.i1}{p2.i2}

where `GLB=global load balancer`, `(pool1)=pool in a cloud env`, `[LB]=load balancer of a pool`, `{p.i}=instance in a pool`
