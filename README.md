# Social Gitter API

Run a POSTGRES container with:

```
docker run -d  -p 5432:5432  --name postgres -e POSTGRES_DB=gitter -e POSTGRES_PASSWORD=gitter123  -e POSTGRES_USER=gitter -e PGDATA=/var/lib/postgresql/data/pgdata  postgres
```

Pour les publications, le format de retour de l'API devrait être:
```
{
  username: "uname",
  userId: 134
  content: "blabla"
  codeId: 673,
  sharedPublicationId: 67,
  parentPublicationId: null,
  likedBy: [
    1,
    2,
    3  
  ]
}
```
Pour les comments:
```
{
  username: "uname",
  userId: 134
  content: "blabla"
  likedBy: [
    1,
    2,
    3  
  ]
}
```