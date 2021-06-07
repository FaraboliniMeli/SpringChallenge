# API Social MeLi

## IMPORTANTE

Os JSONs que usei como banco de dados foram commitados vazios, para poderem testar melhor todos os endpoints da maneira que quiserem.

## Descrição
API desenvolvida em Java usando o Framework Spring, para uma rede social de vendedores e compradores do MeLi.

## Endpoints

### Cadastro de conta para comprador(POST)

* http://localhost:8080/users/buyer/register

Cadastra um novo usuário do tipo comprador. Recebe um JSON no formato:

{
	"username": "testBuyer"
}

### Contas de compradores registradas (GET)

* http://localhost:8080/users/buyer/list

Retorna todas as contas registradas do tipo comprador. Não recebe nenhum parâmetro.

### Cadastro de conta para vendedor(POST)

* http://localhost:8080/users/seller/register

Cadastra um novo usuário do tipo vendedor.
Recebe um JSON no formato:

{
	"username": "testSeller"
}

### Contas de vendedores registradas(GET)

* http://localhost:8080/users/seller/list

Retorna todas as contas registradas do tipo vendedor. Não recebe nenhum parâmetro.

### Contas registradas (GET)

* http://localhost:8080/users/list

Retorna todas as contas registradas na rede social MeLi.

### Seguir vendedor(POST)

* http://localhost:8080/users/{userId}/follow/{userIdToFollow}

Recebe como parâmetro o ID do usuário que vai seguir um vendedor, e o ID do vendedor a ser seguidor.

### Quantos seguidores eu tenho?(GET)

* http://localhost:8080/users/{userID}/followers/count

Recebe como parâmetro um ID de vendedor e retorna quantos seguidores esse vendedor em um JSON do seguinte formato:

{
	"userId": 1,
	"username": "testSeller",
	followersCount: 5
}

### Lista de seguidores (GET)

* http://localhost:8080/users/{userId}/followers/list

Recebe como parâmetro um ID de vendedor e retorna uma lista de usuários que seguem ele. JSON no formato:

{
	"userId" 1,
	"username": "testSeller",
	"followers": [
		{
			"userId": 2,
			"username "testBuyer"
		},
		{
			"userId": 3,
			"username": "Seller"
		}
	]
}

Essa lista pode ser ordenada no seguinte Endpoint:

* http://localhost:8080/users/{userId}/followed/orderedList?order=sortBy

Com as seguintes possibilidades de ordenação:
* name_asc
* name_desc

### Lista de vendedores que sigo (GET)

* http://localhost:8080/users/{userId}/followed/list

Recebe como parâmetro um ID de usuário e retorna uma lista de vendedores que esse usuário segue. JSON no formato:

{
	"userId" 1,
	"username": "testSeller",
	"followed": [
		{
			"userId": 1,
			"username "testSeller"
		},
		{
			"userId": 3,
			"username": "Seller"
		}
	]
}

Essa lista pode ser ordenada no seguinte Endpoint:

* http://localhost:8080/users/{userId}/followed/orderedList?order=sortBy

Com as seguintes possibilidades de ordenação:
* name_asc
* name_desc

### Postar produto para venda (POST)

* http://localhost:8080/products/newpost

Cria um post de venda de um determinado produto para o vendedor. Recebe um json no formato:
{
	"userId": 1,
    "postId": 1,
    "date": "06-06-2021",
    "detail": {
        "productId": 1,
        "productName": "Super Mario 3D All Stars",
        "type": "Games",
        "brand": "Nintendo",
        "color": "none",
        "notes": "Nintendo Switch game"
    },
    "category": 100,
    "price": 199.99
}

### Listar posts feitos nas últimas duas semanas pelos vendedores que sigo (GET)

* http://localhost:8080/products/followed/{userId}/list

Recebe como parâmetro um ID de usuário, e retorna um JSON contendo os vendedores que esse usuário segue e os posts feitos por eles nas últimas duas semanas:

{
    "userId": 1,
    "posts": [
        {
            "postId": 1,
            "date": "2021-06-07",
            "detail": {
                "productId": 1,
                "productName": "Super Mario 3D All Stars",
                "type": "Games",
                "brand": "Nintendo",
                "color": "none",
                "notes": "Nintendo Switch game"
            },
            "category": 100,
            "price": 199.99,
            "hasPromo": false,
            "discount": 0.0
        },
        {
            "postId": 2,
            "date": "2021-05-28",
            "detail": {
                "productId": 1,
                "productName": "Super Mario 3D All Stars",
                "type": "Games",
                "brand": "Nintendo",
                "color": "none",
                "notes": "Nintendo Switch game"
            },
            "category": 100,
            "price": 199.99,
            "hasPromo": false,
            "discount": 0.0
        }
    ]
}

Essa lista pode ser ordenada no seguinte Endpoint:

* http://localhost:8080/products/followed/{userId}/orderedList?order=sortBy

Com as seguintes possibilidades de ordenação:
* date_asc
* date_desc

### Deixar de seguir vendedor (POST)

* http://localhost:8080/users/{userId}/unfollow/{userIdToUnfollow}

Recebe como parâmetro um ID de usuário que deseja deixar de seguir um vendedor, e o ID desse vendedor que receberá um unfollow.

### Postar um produto em promoção (POST)

* http://localhost:8080/products/newPromoPost

Permite que o vendedor faça um post de um produto que entrou em promoção e informe o valor de desconto. Recebe um JSON no seguinte formato:

{
    "userId": 1,
    "postId": 2,
    "date": "06-06-2021",
    "detail": {
        "productId": 1,
        "productName": "Super Mario 3D All Stars",
        "type": "Games",
        "brand": "Nintendo",
        "color": "none",
        "notes": "Nintendo Switch game"
    },
    "hasPromo": true,
    "discount": 0.10,
    "category": 100,
    "price": 199.99
}

### Quantos produtos o vendedor tem em promoção? (GET)

* http://localhost:8080/products/{userId}/countPromo

Recebe como parâmetro o ID do vendedor e retorna um JSON contendo quantos produtos em promoção esse vendedor postou:

{
	"userId": 1,
	"username": "testSeller",
	"promoProductsCount": 15
}

### Posts de produtos em promoção (GET)

* http://localhost:8080/products/{userId}/list

Recebe como parâmetro o ID do vendedor e retorna uma lista contendo todos os produtos em promoção que esse vendedor postou para venda.

{
    "userId": 1,
    "username": "testSeller",
    "promoPosts": [
        {
            "postId": 2,
            "date": "2021-06-06",
            "detail": {
                "productId": 1,
                "productName": "Super Mario 3D All Stars",
                "type": "Games",
                "brand": "Nintendo",
                "color": "none",
                "notes": "Nintendo Switch game"
            },
            "category": 100,
            "price": 199.99,
            "hasPromo": true,
            "discount": 0.1
        }
    ]
}
