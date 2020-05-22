package com.trendyol.shoppingservice.infrastructure.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.trendyol.shoppingservice.domain.ShoppingCart;
import com.trendyol.shoppingservice.domain.ShoppingRepository;
import com.trendyol.shoppingservice.infrastructure.DatabaseConfiguration;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DefaultMongoRepository implements ShoppingRepository {

    private static final ReplaceOptions REPLACE_OPTIONS
            = ReplaceOptions.createReplaceOptions(new UpdateOptions().upsert(true));
    private final com.mongodb.client.MongoClient mongoClient;
    private final MongoCollection<ShoppingCart> mongoCollection;

    public DefaultMongoRepository(
            DatabaseConfiguration configuration,
            com.mongodb.client.MongoClient mongoClient) {
        this.mongoClient = mongoClient;

        final String mongoDbName = configuration.getMongoDbName();
        final String mongoCollectionName = configuration.getMongoCollectionName();


        final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        final MongoDatabase mongoDatabase = this.mongoClient.getDatabase(mongoDbName).withCodecRegistry(pojoCodecRegistry);
        mongoCollection = mongoDatabase.getCollection(mongoCollectionName, ShoppingCart.class);
    }

    public Optional<ShoppingCart> findByCartId(final String chartId) {

        final Bson filter = Filters.eq("cartId", chartId);

        final List<ShoppingCart> shoppingCart = mongoCollection
                .find(filter)
                .limit(1)
                .into(new ArrayList<>());

        return shoppingCart != null && !shoppingCart.isEmpty() ? Optional.ofNullable(shoppingCart.get(0)) : Optional.empty();

    }

    @Override
    public void save(final ShoppingCart shoppingCart) {

        final Bson filter = Filters.eq("cartId", shoppingCart.getCartId());

        final Optional<ShoppingCart> maybeShopping = findByCartId(shoppingCart.getCartId());

        if (maybeShopping.isEmpty()) {
            mongoCollection.insertOne(shoppingCart);
            return;
        }

        mongoCollection.replaceOne(filter, shoppingCart);
    }
}
