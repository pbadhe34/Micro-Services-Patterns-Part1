																																																																																																																																																																																																																																																																																															

Here are all the individual services: 
- `eureka-service-discovery` - the Eureka-based service registry and discovery sever
- `zuul-api-gateway` - the Zuul-based API Gateway server
- `authorization-server` - the Authoization Server
- `app-service` - the Resource Server

 
When starting up the services, the sequence should be - 
`eureka-service-discovery` first, everything else after. 



## Run
Once everything starts up, try to access the 'App-service'  , through the Gateway: 

 http://localhost:8765/app-service 

  OR even direct

   http://localhost:8870/app-service

Or, if you're authenticating with an admin: 

 http://localhost:8765/app-service/secret

You'll be redirected to the Authorization Server to authenticate. 
Use the following credentials: `user`/`password` or `admin`/`admin`. 
And approve the authorization for the `fooScope` OAuth scope. 



## High-Level Flow

 
Browser Request to API Gateway (APIG) 

             Authorization Server (AS)

   │ APIG/app-service                │                                  │
   ├────────────────────────────────────────>│                                  │
   │ Location:http://APIG/login              │                                  │
   │<────────────────────────────────────────│                                  │
   │ http://APIG/login                       │                                  │
   ├────────────────────────────────────────>│                                  │
   │ Location:http://APIG/AS/oauth/authorize │                                  │
   │<────────────────────────────────────────│                                  │
   │ http://APIG/AS/oauth/authorize          │                                  │
   ├────────────────────────────────────────>│                                  │
   │                                         │      /AS/oauth/authorize         │
   │                                         ├─────────────────────────────────>│
   │                                         │                                  ├──┐
   │                                         │                                  │  │ Not authorized
   │                                         │                                  │<─┘
   │                                         │  Location:http://APIG/AS/login   │
   │                                         │<─────────────────────────────────┤
   │ Location:http://APIG/AS/login           │                                  │
   │<────────────────────────────────────────│                                  │
   │ http://APIGAS/AS/login                  │                                  │
   ├────────────────────────────────────────>│                                  │
   │                                         │            /AS/login             │
   │                                         ├─────────────────────────────────>│
   │                                         │           LOGIN FORM             │
   │                                         │<─────────────────────────────────┤
   │           LOGIN FORM                    │                                  │
   │<────────────────────────────────────────┤                                  │
```

 

 

### `zuul.authorization-server-1.sensitiveHeaders`
Need the Cookie to be passed through from the Authorization Server

