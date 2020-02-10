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
  "email": "jr@test.com",
  "password": null
}
```

## Get All Workouts: GET /api/workout?start={start}&count={count}
> curl -u jr@test.com:password1 http://localhost:5000/api/workout
```
[
  {
    "id": "ad95d788-64e3-46a2-9509-a637bb2c8845",
    "email": "jr@test.com",
    "date": "+120999-11-11T00:00:00.000+0000",
    "duration": 4
  },
  {
    "id": "b215cd01-f12d-4bb0-916b-6a8fbf69a02d",
    "email": "jr@test.com",
    "date": "+33333-03-31T00:00:00.000+0000",
    "duration": 3333
  }
]
```

## Get Workout: GET /api/workout/{workoutId}
> curl -u jr@test.com:password1 http://localhost:5000/api/workout/ad95d788-64e3-46a2-9509-a637bb2c8845
```
{
    "date": "+120999-11-11T00:00:00.000+0000",
    "duration": 4,
    "email": "jr@test.com",
    "id": "ad95d788-64e3-46a2-9509-a637bb2c8845"
}
```

## Create Workout: POST /api/workout
> curl -i -u jr@test.com:password1 -X POST -H "Content-Type: application/json" http://localhost:5000/api/workout -d '{"date": "2020-10-30", "duration": 123}'
```
HTTP/1.1 201 
...
Location: http://localhost:5000/api/workout/15a94d25-7365-4ad3-9355-eed4fdec1875
```

## Delete Workout DELETE /api/workout/{workoutId}
> curl -i -u jr@test.com:password1 -X DELETE http://localhost:5000/api/workout/ad95d788-64e3-46a2-9509-a637bb2c8845
