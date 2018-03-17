# Template
Acts as a template for any microservice that needs to be created. It provides the following:

* Using GET, GET{id}, POST, PATCH{id}, DELETE{id}
* Common Http Status codes
* Exception handling

## Run
mvn spring-boot:run

## Example
### Add
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X POST -d 'param1=aaa&param2=bbb&param3=ccc' http://localhost:9000/template/v1.0/items

### Get (list)
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X GET http://localhost:9000/template/v1.0/items

### Get Item
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X GET http://localhost:9000/template/v1.0/items/{use_an_id_from_the_list_call}

### Partial Update Item
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X PATCH -d 'param3=ddd' http://localhost:9000/template/v1.0/items/{use_an_id_from_the_list_call}

### Delete Item
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X DELETE http://localhost:9000/template/v1.0/items/{use_an_id_from_the_list_call}
