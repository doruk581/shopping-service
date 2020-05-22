# shopping-service

Shopping Service


# SISTEMI CALISTIRMAK
Calistirilacak ortamda Docker yüklü olması gerekmektedir.
Eğer Docker macOS platformunda çalışıyor ise Docker Enginenin en az 4 gb memory limitinin olduğuna emin olunuz.
docker-compose up komutu ile sistemi kaldırabilirsiniz.
# API BLUEPRINT
## Cart Add Item [/shopping/addItem]
### Adding to Shop Cart [POST]

+ commandId (required, string) - Chart Id
+ id (required, string) - Product id
+ quantity (required, number) - Quantity of Products
+ couponId (string) - Coupon Id


+ Request (application/json;charset=utf-8)

    + Body

            {
                "commandId":"0531155",
                "id": "2"
                "quantity": 10,
                "couponId": "COUP2"
            }

+ Response 204 (application/json;charset=utf-8)


## Derleme

### Build

```
./gradlew clean build
```

### Birim Testleri çalıştırmak

```
./gradlew clean test
```

## Çalıştırma

Projeyi derlemek için

```
./gradlew clean bootJar
```

Derlenen projeyi çalıştırmak için

```
java -jar build/libs/shopping-service-all-0.0.1-SNAPSHOT.jar 
```




