# Demo project for JWT in API gateway use case

In some API gateway scenario, we may need to separate the JWT generation and JWT verification in different server, with the mechanism of RSA.
- API (gateway) side holds the public key, to verify the JWT token.
- Consumer side holds the private key, to generate the JWT token.


Please check the 2 projects for reference.

# Security consideration
- The keystore in the client project, you should generate them separately for Production and UAT environment.
- The password of the keystore, you should encrypt them properly instead of the way you see in the sample code.
- The public key, maybe you put it somewhere else instead of putting it in the source, to make sure you can use different version for UAT and Producton.