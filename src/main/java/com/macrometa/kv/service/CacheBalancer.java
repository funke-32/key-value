package com.macrometa.kv.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("cache")
@Component
public class CacheBalancer {

    /* New node should not receive traffic till cache is balanced
     1. connect to registry
     2. get all key-value instances
     3. creating getAll REST (paginated) request for all the nodes , excluding self
     4. keep the matching objects in cache, and un-matching objects should be stored in a separate list for each node
     5. for all un-matching keys in the list, make another round of request by key, and repeat step 4 till un-matching objects are zero ot max iterations are done
     */


}