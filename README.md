# Library System
---
Used technologies:

1. Java 8
2. Maven
3. Spring Framework
	- Spring Boot
	- Spring Web MVC for REST API
4. MySQL DB

---

## 1. REST endpoints


1. Add new author.

...

	*PUT/authors*
...
...
	

		{
			"firstName": "Frederick",
			"lastName": "Forsyth"
		}
...
	
2. Add new genre.

...
*PUT/genres*
...

...
		{
			"name": "fiction",
		}
...
	
3. Create new book.
...
	
		*PUT/books*
...
...
		{
			"title": "Java",
			"isbn": "abc123",
			"publishedAt": "2000-01-01",
			"genreId": 1,
			"authorId": 1

		}
...
	
	
4.Add new specimen to existing book.
...
	
		*POST/books/:bookId/specimens*
...
...
		{
			"code": "qwerty"
		}
...

		-example respone:
...
		{
			"id": 1,
			"title": "Java",
			"isbn": "abc123",
			"authors": [
				{
					"firstName": "Frederick",
					"lastName": "Forsyth"
				}
						],
			"publishedAt": "2000-01-01",
			"genre": {
				"id": 1,
				"name": "fiction"
			},
			"specimensCode": [
				"aaa123",
				"qwerty"
			]
		}
...

	
5.Register new library client.
	
		PUT/clients
...
		{
			"firstName": "John",
			"lastName": "Doe",
			"documentNumber": "ABC999999",
			"pesel": "99010199999",
			"street": "Trafalgar Sq.",
			"city": "London"

		}
...
	
6.Lend specimen of book to conrete client.
	
		PUT/clients/:clientId/lendings
...
		{
			"code": "ccc"

		}
...

		-example respone:

...
		{
			"firstName": "Jan",
			"lastName": "Kowalski",
			"documentNumber": "ASD99",
			"pesel": "90010604918",
			"address": {
				"street": "Warszawska",
				"city": "Poznań"
				},
			"lendingsHistory": [
		{
				"lendingDate": "2018-01-10",
		    "returnDate": "2018-01-11",
		    "specimenCode": "A123",
		    "title": "Dziady"
		},
		{
		    "lendingDate": "2018-01-10",
		    "returnDate": "9999-01-01",
		    "specimenCode": "A111",
		    "title": "Kordian"
		},
		{
		    "lendingDate": "2018-01-10",
		    "returnDate": "9999-01-01",
		    "specimenCode": "B111",
		    "title": "Kordian"
		},
		{
		    "lendingDate": "2018-02-22",
		    "returnDate": "9999-01-01",
		    "specimenCode": "ccc",
		    "title": "Dziady"
		}
	    ],
			"actualLendings": [
				"Kordian",
				"Kordian",
				"Dziady"
			]
		}
...
	
7. Search for all books.
	
		GET/books
		-example respone:
...
		[
			{
				"id": 1,
				"title": "Java",
				"isbn": "abc123"
			},
			{
				"id": 2,
				"title": "Spring",
				"isbn": "chud7ge72"
			}
		]
...

8. Search for specified book.
	
		GET/books/:bookId
		-example respone:
...
		{
	    	"id": 1,
	    	"title": "Java",
	   	"isbn": "ABC-123",
	   	"authors": [
		{
		    "id": 1,
		    "firstName": "Bert",
		    "lastName": "Bates"
		},
		{
		    "id": 2,
		    "firstName": "Kathy",
		    "lastName": "Sierra"
		}
	    ],
	    "publishedAt": "2008-01-01",
	    "genre": {
		"id": 1,
		"name": "technology"
	    },
	    "specimensCode": [
		"A123",
		"B123",
		"C123"
	    ]
...
	

9. Search for specified client and his lending history.
	
		GET/clients/:clientId

		- example response:

...
		{
			"firstName": "Jan",
			"lastName": "Kowalski",
			"documentNumber": "ASD99",
			"pesel": "90010604918",
			"address": {
				"street": "Warszawska",
				"city": "Poznań"
			},
	    "lendingsHistory": [
		{
		    "lendingDate": "2018-01-10",
		    "returnDate": "9999-01-01",
		    "specimenCode": "A123",
		    "title": "Dziady"
		},
		{
		    "lendingDate": "2018-02-22",
		    "returnDate": "9999-01-01",
		    "specimenCode": "ccc",
		    "title": "Dziady"
		},
		{
		    "lendingDate": "2018-01-10",
		    "returnDate": "9999-01-01",
		    "specimenCode": "A111",
		    "title": "Kordian"
		},
		{
		    "lendingDate": "2018-01-10",
		    "returnDate": "9999-01-01",
		    "specimenCode": "B111",
		    "title": "Kordian"
		}
	    ],
		"actualLendings": [
			"Dziady",
			"Dziady",
			"Kordian",
			"Kordian"
			]
		}
...

10.Return specimen to library.
	
		DELETE/clients/:clientId/lendings

...
		{
			"code": "A123"

		}
...

		- example respone:

...
		{
			"firstName": "Jan",
			"lastName": "Kowalski",
			"documentNumber": "ASD99",
			"pesel": "90010604918",
			"address": {
				"street": "Warszawska",
				"city": "Poznań"
			},
	    "lendingsHistory": [
		{
		    "lendingDate": "2018-01-10",
		    "returnDate": "2018-01-11",
		    "specimenCode": "A123",
		    "title": "Dziady"
		},
		{
		    "lendingDate": "2018-02-22",
		    "returnDate": "9999-01-01",
		    "specimenCode": "ccc",
		    "title": "Dziady"
		},
		{
		    "lendingDate": "2018-01-10",
		    "returnDate": "9999-01-01",
		    "specimenCode": "A111",
		    "title": "Kordian"
		},
		{
		    "lendingDate": "2018-01-10",
		    "returnDate": "9999-01-01",
		    "specimenCode": "B111",
		    "title": "Kordian"
		}
	    ],
		"actualLendings": [
			"Dziady",
			"Kordian",
			"Kordian"
			]
		}
...
	
11.Remove specimen from library stock.
	
		DELETE/books/:bookId/specimens

...
		{
			"code": "ccc"
		}
...

		- example response:

...
		{
			"id": 1,
			"title": "Dziady",
			"isbn": "ABC-123",
			"authors": [
		{
		    "id": 1,
		    "firstName": "Adam",
		    "lastName": "Mickiewicz"
		},
		{
		    "id": 2,
		    "firstName": "Juliusz",
		    "lastName": "Słowacki"
		}
	    ],
			"publishedAt": "1960-01-01",
			"genre": {
				"id": 1,
				"name": "fiction"
			},
			"specimensCode": [
				"A123",
				"B123"
			]
		}
...
	
12.Get genre list.
	
		GET/genres

		- example response:
...

		[
			{
				"id": 1,
				"name": "fiction"
			},
			{
				"id": 2,
				"name": "poetry"
			},
			{
		"id": 3,
		"name": "criminal"
			}
		]
...

13.Get authors list.
	
		GET/authors

		- example response:

...
		[
			{
				"id": 1,
				"firstName": "Adam",
				"lastName": "Mickiewicz"
			},
			{
				"id": 2,
				"firstName": "Juliusz",
				"lastName": "Słowacki"
			},
			{
				"id": 3,
				"firstName": "Frederick",
				"lastName": "Forsyth"
			}
		]
...
	
14. Assign author to existing book.
	
		PUT/books/:booksId/authors

...
		{
			"authorId": 1
		}
...

	
	
