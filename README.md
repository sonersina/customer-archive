# customer-archive

# ER diagram

![Screenshot](er_diagram.jpg)


# Veritabanını Oluşturma

#### Bu projede yerel Docker PostgreSQL veritabanı kullanılır. Veritabanını kullanmak için makine üzerinde Docker'ın yüklü olması gerekir.<br><br> Veritabanını çalıştırmak için, terminaldeki aşağıdaki komutu çalıştırın:
```json
docker run --name some-postgres -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres
```
#### Bu, PostgreSQL görüntüsündeki Docker bir konteynerini başlatacak ve aşağıdaki kimlik bilgileriyle bir veritabanı oluşturacaktır:

#### - Host: `localhost`
#### - Port: `5432`
#### - User: `postgres`
#### - Password: `admin` 

# Veritabanına Bağlanma

#### Veritabanına Spring Boot projenizden bağlanmak için, aşağıdaki özelliklerle 'application.yml' dosyasını güncelleyin:

- spring:
- datasource:
- url: jdbc:postgresql://localhost:5432/postgres
- username: postgres
- password: admin


---------------------------------------------

# API Dökümantasyonu

## AuthController
### Register

- Request URL: `/api/v1/auth/register`
- Request Method: `POST`
- Request Body:

```json
{
  "firstName" : "Soner Sina",
  "lastName" : "Ayıran",
  "username" : "sonersina",
  "email" : "ayiransonersina@gmail.com",
  "password" : 123456
}
```

- Response : 
```json
{
  "message": "Basariyla Kayıt Olundu!",
  "data": {
    "username": "sonersina",
    "email": "ayiransonersina@gmail.com"
  }
}
```

### Login

- Request URL: `/api/v1/auth/login`
- Request Method: `POST`
- Request Body:

```json
{
  "username" : "sonersina",
  "password" : 123456
}
```

- Response :
```json
{
  "message": "Basariyla Giris Yapıldı!",
  "data": {
    "username": "sonersina",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb25lcnNpbmEiLCJpYXQiOjE2NzYyMDA3MjAsImV4cCI6MTY3NjIwNDMyMH0.Fj6htps2n_DIiDIEsYJvMaOWoye7O83yT-tj0FbN8x0"
  }
}
```


# CustomerController

### Create Customer

- Request URL: `/customer/create`
- Request Method: `POST`
- Request Body:

```json
{
  "name" : "Müşteri Adı",
  "email" : "musteri1@gmail.com",
  "address" : "Müsteri Adresi",
  "phone" : "5555555555"
}
```

- Response :
```json
{
  "message": "Müşteri Başarıyla Oluşturuldu!",
  "data": {
    "id": 2,
    "name": "Müşteri Adı",
    "email": "musteri1@gmail.com",
    "address": "Müsteri Adresi",
    "phone": "5555555555",
    "fileList": null
  }
}
```

### Update Customer

- Request URL: `/customer/update/{id}`
- Request Method: `PUT`
- URL Param: `id=[Long]`
- Request Body:

```json
{
  "name" : "Musteri Güncellenmis Ad",
  "email" : "guncellenmiseposta@gmail.com",
  "address" : "Güncellenmiş Adres",
  "phone" : "5553333333"
}
```

- Response :
```json
{
  "message": "Müşteri Başarıyla Güncellendi!",
  "data": {
    "id": 1,
    "name": "Musteri Güncellenmis Ad",
    "email": "guncellenmiseposta@gmail.com",
    "address": "Güncellenmiş Adres",
    "phone": "5553333333",
    "fileList": []
  }
}
```
### Read Customer

- Request URL: `/customer/read/{id}`
- Request Method: `GET`
- URL Param: `id=[Long]` 
- Response :
```json
{
  "message": "Müşteri Başarıyla Görüntülendi!",
  "data": {
    "id": 1,
    "name": "Musteri Güncellenmis Ad",
    "email": "guncellenmiseposta@gmail.com",
    "address": "Güncellenmiş Adres",
    "phone": "5553333333",
    "fileList": []

  }
}
```

### Delete Customer

- Request URL: `/customer/delete/{id}`
- Request Method: `DELETE`
- URL Param: `id=[Long]`
- Response :
```json
{
  "message": "Müşteri Başarıyla Silindi!",
  "data": null
}
```
### List Customer

- Request URL: `/customer/list`
- Request Method: `GET`
- URL Param: `page=[Long] , size=[Long]`

- Response :
```json
{
  "message": "Başarıyla Listelendi!",
  "data": {
    "content": [
      {
        "id": 1,
        "name": "Musteri Güncellenmis Ad",
        "email": "guncellenmiseposta@gmail.com",
        "address": "Güncellenmiş Adres",
        "phone": "5553333333",
        "fileList": []
      },
      {
        "id": 2,
        "name": "Müşteri Adı",
        "email": "musteri1@gmail.com",
        "address": "Müsteri Adresi",
        "phone": "5555555555",
        "fileList": [
          {
            "id": "083d4978-6181-4f7f-83ca-6354131cfa0c",
            "name": "d404835f-9aaa-429d-8439-0330c2ac4b82.pdf",
            "path": "store/d404835f-9aaa-429d-8439-0330c2ac4b82.pdf",
            "mimeType": "application/pdf",
            "customerId": 2
          }
        ]
      }
    ],
    "pageable": {
      "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
      },
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 10,
      "paged": true,
      "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 10,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
  }
}
```

## FileController

### Create File

- Request URL: `/file/upload`
- Request Method: `POST`
- Request Body(Form-Data):

```yaml
{
  Key: file, Value: sample.pdf
  Key: customerId, Value: 2
}
```

- Response :
```json
{
  "message": "Dosya Başarıyla Oluşturuldu!",
  "data": {
    "id": "083d4978-6181-4f7f-83ca-6354131cfa0c",
    "name": "d404835f-9aaa-429d-8439-0330c2ac4b82.pdf",
    "path": "store/d404835f-9aaa-429d-8439-0330c2ac4b82.pdf",
    "mimeType": "application/pdf",
    "customerId": 2
  }
}
```

### Update File

- Request URL: `/file/update`
- Request Method: `PUT`
- Request Body(Form-Data):

```yaml
{
  Key: file, Value: sample.pdf
  Key: fileId, Value: 083d4978-6181-4f7f-83ca-6354131cfa0c
}
```

- Response :
```json
{
  "message": "Dosya Başarıyla Güncellendi!",
  "data": {
    "id": "a8bc07fb-ed6d-4b2a-a1b9-3fc1dea69561",
    "name": "f71e970d-6dce-4d19-aad9-fecb5c47d7ff.pdf",
    "path": "store/f71e970d-6dce-4d19-aad9-fecb5c47d7ff.pdf",
    "mimeType": "application/pdf",
    "customerId": 1
  }
}
```

### Get File

- Request URL: `/file/get-file/{id}`
- Request Method: `GET`
- URL Param: `id=[String]`


- Response :

  - Belirtilen ID'ye sahip bir binary dosya, otomatik olarak client tarafından indirilecektir.


### Get File Info

- Request URL: `/file/get-file-info/{id}`
- Request Method: `GET`
- URL Param: `id=[String]`

- Response :
```json
{
  "message": "Dosya Bilgileri Başarıyla Görüntülendi!",
  "data": {
    "id": "8d91d62d-aa02-483a-bc1b-ce8c3171479b",
    "name": "8e745741-f035-4106-ace4-094561246856.pdf",
    "path": "store/8e745741-f035-4106-ace4-094561246856.pdf",
    "mimeType": "application/pdf",
    "customerId": 1
  }
}
```

### Delete File

- Request URL: `/file/delete/{id}`
- Request Method: `DELETE`
- URL Param: `id=[String]`
- Response :
```json
{
  "message": "Dosya Başarıyla Silindi!",
  "data": null
}
```

### List Files

- Request URL: `/file/list-files`
- Request Method: `GET`
- URL Param: `page=[Long] , size=[Long]`

- Response :
```json
{
  "message": "Başarıyla Listelendi!",
  "data": {
    "content": [
      {
        "id": "8d91d62d-aa02-483a-bc1b-ce8c3171479b",
        "name": "8e745741-f035-4106-ace4-094561246856.pdf",
        "path": "store/8e745741-f035-4106-ace4-094561246856.pdf",
        "mimeType": "application/pdf",
        "customerId": 1
      },
      {
        "id": "f652aa74-bc71-4352-b395-a8eb559d36ec",
        "name": "2e49e7dd-9281-4fb1-8796-fb18128c678c.pdf",
        "path": "store/2e49e7dd-9281-4fb1-8796-fb18128c678c.pdf",
        "mimeType": "application/pdf",
        "customerId": 1
      },
      {
        "id": "f2eb1838-2aac-4df9-af72-0065e7f6929c",
        "name": "fae062ff-d46e-4ba9-ad42-6a1d9a240e89.pdf",
        "path": "store/fae062ff-d46e-4ba9-ad42-6a1d9a240e89.pdf",
        "mimeType": "application/pdf",
        "customerId": 1
      }
    ],
    "pageable": {
      "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
      },
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 20,
      "paged": true,
      "unpaged": false
    },
    "last": true,
    "totalElements": 3,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "numberOfElements": 3,
    "first": true,
    "empty": false
  }
}
```

# Notlar

### Auth ve Register Endpoinleri hariç tüm endpointler authentication'a ihtiyaç duyar.
