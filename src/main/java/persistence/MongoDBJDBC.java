package persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;
import org.bson.Document;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 获取MongoDB连接及操作
 * Created by pjh on 2017/2/10.
 */
public class MongoDBJDBC {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBJDBC.class);

    private static MongoDatabase mongoDatabase;

    private static MongoClient mongoClient;

    private static synchronized void setMongoClient(){
        try{
            mongoClient = new MongoClient(Config.mongoDBHost, Config.mongoDBPort );
        }catch (Exception e){
            logger.error("数据库连接出错");
        }
    }

    public static  MongoDatabase getMongoDatabase(){
        if (mongoClient == null){
            setMongoClient();
        }
        mongoDatabase = mongoClient.getDatabase(Config.databaseName);
        return mongoDatabase;
    }

    public static void insert(String key, String value){
        getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection(Config.dbCollection);
        Document document = new Document(key, value);
        collection.insertOne(document);
    }

    public static void insert(HashMap<String, String> map){
        getMongoDatabase();
        Document document = new Document();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            document.append(entry.getKey(), entry.getValue());
        }
        MongoCollection<Document> collection = mongoDatabase.getCollection(Config.dbCollection);
        collection.insertOne(document);
    }

    public static void insert(List<Document> list){
        getMongoDatabase();
        MongoCollection<Document> collection = mongoDatabase.getCollection(Config.dbCollection);
        collection.insertMany(list);
    }
}
