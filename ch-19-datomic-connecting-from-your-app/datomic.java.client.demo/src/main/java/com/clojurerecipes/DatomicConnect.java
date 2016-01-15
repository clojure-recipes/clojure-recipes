package com.clojurerecipes; 

import java.util.Collection; 
import java.util.List;
import datomic.Connection; 
import datomic.Database; 
import datomic.Entity; 
import datomic.Peer;

public class DatomicConnect {
	public static void main(String[] args) {
		String uri = "datomic:free://localhost:4334/bookdemo"; 
		Connection connection = Peer.connect(uri);
		Database database = connection.db();
		Collection<List<Object>> results = Peer.q(
			"[:find ?bo :where [_ :borrow/title ?t][?bo :book/title ?t]]",
			database);
		List<Object> result = results.iterator().next(); Object entityId = result.get(0);
		Entity entity = database.entity(entityId); 
		System.out.println(entity.keySet()); 
		System.out.println("Displaying the value of the entity's book name..."); 
		System.out.println(entity.get(":book/title"));
	} 
}

