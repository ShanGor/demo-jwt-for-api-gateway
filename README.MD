# Demo project for JWT in API gateway use case

In some API gateway scenario, we may need to separate the JWT generation and JWT verification in different server, with the mechanism of RSA.
- API (gateway) side holds the public key, to verify the JWT token.
- Consumer side holds the private key, to generate the JWT token.

This is for `Spring MVC` with `Java7`, if you want `webflux`, please ref to [this one](https://github.com/ShanGor/demo-java11-webflux-jwt-kotlin)

Please check the 2 projects for reference.

# Security consideration
- The keystore in the client project, you should generate them separately for Production and UAT environment.
- The password of the keystore, you should encrypt them properly instead of the way you see in the sample code.
- The public key, maybe you put it somewhere else instead of putting it in the source, to make sure you can use different version for UAT and Producton.
- Expiry time in the sample code is 600 seconds, you should make it within 30 seconds in Production.