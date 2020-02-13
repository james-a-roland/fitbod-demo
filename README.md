# Summary
A basic CRUD application presenting various API endpoints built on top of Spring + Redis, using Heroku for deployment.
Supported use cases are specified in the "API Endpoints" section with examples.

# Local Deployment
Ensure you've got a Redis instance running via `redis-server`.
Then, build and run in dev mode via `heroku local dev`. 
The debug port is `5005`.

> To specify a Redis server/host, you can simply change it in application.properties.
# API Endpoints
## Registration: POST /api/user
> curl -X POST -H "Content-Type: application/json" http://localhost:5000/api/user -d '{"email": "jr@test.com", "password": "password1"}'

## Get Current User: GET /api/user
> curl -u jr@test.com:password1 http://localhost:5000/api/user
```
{
  "email": "jr@test.com"
}
```

## Get All Workouts: GET /api/workout?start={start}&count={count}
> curl -u jr@test.com:password1 http://localhost:5000/api/workout
```
[
    {
        "date": "2335-02-23T00:00:00.000+0000",
        "duration": 142,
        "id": "869182e0-bd75-48cb-9912-4bb250658313"
    },
    {
        "date": "1111-04-02T00:00:00.000+0000",
        "duration": 1314,
        "id": "bc0d38ac-afac-420d-aecd-d19894725546"
    },
...
]
```

## Get Single Workout: GET /api/workout/{workoutId}
> curl -u jr@test.com:password1 http://localhost:5000/api/workout/ad95d788-64e3-46a2-9509-a637bb2c8845
```
{
    "date": "+120999-11-11T00:00:00.000+0000",
    "duration": 4,
    "id": "ad95d788-64e3-46a2-9509-a637bb2c8845"
}
```

## Create new Workout: POST /api/workout
> curl -i -u jr@test.com:password1 -X POST -H "Content-Type: application/json" http://localhost:5000/api/workout -d '{"date": "2020-10-30", "duration": 123}'
```
HTTP/1.1 201 
...
Location: http://localhost:5000/api/workout/15a94d25-7365-4ad3-9355-eed4fdec1875
```

## Update existing Workout via ID: POST /api/workout
> curl -i -u jr@test.com:password1 -X POST -H "Content-Type: application/json" http://localhost:5000/api/workout -d '{"date": "2020-10-30", "duration": 123, "id":"1077e2c-5bbd-45c1-9fa6-a89abf905e45"}'
