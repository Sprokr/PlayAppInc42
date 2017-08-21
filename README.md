# PlayAppInc42


## API Endpoints


### 1. AddProfile 
Request Data - 
```
POST /addProfile HTTP/1.1
Host: 108.59.80.115:9000

Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename=""
Content-Type: 


------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="companyInfo"

{
  "name": "Nike",
  "description": "Sports Company",
  "markets": [
    "Sports",
    "Clothing",
    "Health"
  ],
  "foundedOn": "2001-02-02",
  "website": "http://www.nike.co.on",
  "linkedin": "http://linkedin.com/nike/in/",
  "twitter": "http://twitter.com/nike/",
  "facebook": "http://facebook.com/nike/",
  "phoneNumber": 98,
  "fundingDetails": [
    {
      "amount": 14000,
      "date": "2001-01-12",
      "stage": "StageA",
      "investor": "SoftBank"
    },
    {
      "amount": 11000,
      "date": "2001-02-02",
      "stage": "StageA",
      "investor": "IndiaBank"
    }
  ]
}
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```



Response - 
```
{
    "result": {
        "success": true,
        "httpCode": 200
    },
    "content": {
        "profileId": "1e4638dd-1449-4d32-ad1a-581581fcae5c",
        "name": "Nike",
        "description": "Sports Company",
        "logo": "http://108.59.80.115/tmp/32368729-b032-459e-8272-503d47997168_nike.jpg",
        "markets": [
            "Sports",
            "Clothing",
            "Health"
        ],
        "foundedOn": "2001-02-02",
        "website": "http://www.nike.co.on",
        "linkedin": "http://linkedin.com/nike/in/",
        "facebook": "http://facebook.com/nike/",
        "twitter": "http://twitter.com/nike/",
        "phoneNumber": 98,
        "fundingDetails": [
            {
                "amount": 14000,
                "date": "2001-01-12",
                "stage": "StageA",
                "investor": "SoftBank"
            },
            {
                "amount": 11000,
                "date": "2001-02-02",
                "stage": "StageA",
                "investor": "IndiaBank"
            }
        ]
    }
}
```


### 2. GetProfile 

Request Data - 
```
GET /getProfile/:profileId HTTP/1.1
Host: 108.59.80.115:9000
```

Response - 
```
{
    "result": {
        "success": true,
        "httpCode": 200
    },
    "content": {
        "profileId": "1e4638dd-1449-4d32-ad1a-581581fcae5c",
        "name": "Nike",
        "description": "Sports Company",
        "logo": "http://108.59.80.115/tmp/32368729-b032-459e-8272-503d47997168_nike.jpg",
        "markets": [
            "Sports",
            "Clothing",
            "Health"
        ],
        "foundedOn": "2001-02-02",
        "website": "http://www.nike.co.on",
        "linkedin": "http://linkedin.com/nike/in/",
        "facebook": "http://facebook.com/nike/",
        "twitter": "http://twitter.com/nike/",
        "phoneNumber": 98,
        "fundingDetails": [
            {
                "amount": 14000,
                "date": "2001-01-12",
                "stage": "StageA",
                "investor": "SoftBank"
            },
            {
                "amount": 11000,
                "date": "2001-02-02",
                "stage": "StageA",
                "investor": "IndiaBank"
            }
        ]
    }
}
```

## Note : 
As of now, logo images are being stored in /tmp directory at the server machine and might not be accessible from web. We can always put these files at S3, and grant access to everyone for these.
