# Serveless Studies Api :books:
API to store sources of knowledge that serve as study material for students.

Description
----
Initially we understand that the main sources of study for a student are between links (such as a video, for example), articles, books, courses and podcasts.

With that in mind, we created a REST serverless API using Java and the AWS platform to store this data for each student.

Endpoints Documentation
----
This project has all its endpoints documented in **Postman** as a **shared collection**, to get a copy of its endpoints, just click the link below:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/be7d631f455b04a91e76)

If you want to see the documentation on the **swagger**, just click the button below to view:

<p align="left">
    <a href="https://app.swaggerhub.com/apis-docs/thalees/studies-materials/1.0.0">
        <img width="150" height="50" src="https://miro.medium.com/max/1380/1*aKVg84SP5oPV9fwOnbl6yQ.png">
    </a>
</p>

Architecture
----
The architecture built for this project is designed below:

<p align="center">
    <img src="https://user-images.githubusercontent.com/40672950/84583868-5f31e980-add4-11ea-964c-25de73e939b1.png">
</p>

Among the components used are:

Api Gateway
-
Amazon API Gateway is an AWS service for creating, publishing, maintaining, monitoring and protecting REST and WebSocket APIs at any scale. Its role was to redirect all requests to specific lambdas whenever a request is made.

AWS Lambda
-
AWS Lambda allows you to run code without provisioning or managing servers. In this case, we use Java to perform the logic of each lambda (each lambda can be found in this repository in the handler classes)

We aimed to carry out the interactions with the database and return the results in a valid json format for the API Gateway through Lambda.

RDS
-
Amazon Relational Database Service (Amazon RDS) is a web service that facilitates the configuration, operation and dimensioning of a relational database in the AWS Cloud and for this components we have nothing special. RDS was responsible for storing data related to this API.


## Authors

See the list of [Contributors](https://github.com/thalees/serveless-studies-api/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.