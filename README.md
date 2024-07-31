LastaFlute Example Waterfront [![Build Status](https://travis-ci.org/lastaflute/lastaflute-example-waterfront.svg?branch=master)](https://travis-ci.org/lastaflute/lastaflute-example-waterfront)
=======================
example project for LastaFlute, ESFlute (with Elasticsearch/OpenSearch, also JSP)

LastaFlute:  
https://github.com/lastaflute/lastaflute

# Quick Trial
Can boot it by example of LastaFlute:

1. start Elasticsearch 7.x, 8.x or OpenSearch 1.x
2. git clone https://github.com/lastaflute/lastaflute-example-waterfront.git
3. create index: curl -XPUT localhost:9200/maihama -d @dbflute_maihamadb/playes/create-maihama.json
4. prepare database by *ReplaceSchema at DBFlute client directory 'dbflute_maihamadb'  
5. compile it by Java21, on e.g. Eclipse or IntelliJ or ... as Maven project
6. execute the *main() method of (org.docksidestage.boot) WaterfrontBoot
7. access to http://localhost:8099/waterfront  
and login by user 'Pixy' and password 'sea', and can see debug log at console.

*ReplaceSchema
```java
// call manage.sh at lastaflute-example-waterfront/dbflute_maihamadb
// and select replace-schema in displayed menu
...:dbflute_maihamadb ...$ sh manage.sh
```

*main() method
```java
public class WaterfrontBoot {

    public static void main(String[] args) {
        new TomcatBoot(8099, "/waterfront").useTldDetect().asDevelopment(isNoneEnv()).bootAwait();
    }
}
```

# Information
## License
Apache License 2.0

## Official site
comming soon...
