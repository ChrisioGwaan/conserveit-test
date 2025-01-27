
<div align="center">

<p>Conserve It Coding Test</p>

</div>

---

## Branch

- **feature**: Main branch for production.
- **dev**: Implementing new features, bug fixes, debugging, and other development tasks.

## Version

| Tool                        | Version   |
|-----------------------------|-----------|
| Java                        | 21        |
| Maven                       | \>= 3.9.0 |

## Maven Build

```bash
mvn clean install
```

## Docker Image Build Support

```bash
docker build -t controlbg:<version_tag> . 
```

## Project Description

### Database Records

`db/schema.sql` - Contains the schema design for the database.

### Assumption

There are six tables in the database, and five of them are related to the task requirements.
`sys_user` is used to store user information and generate JWT token to authenticate the APIs. 
Firstly, I assume that the `buidling` table is the main table that contains the building information, and the other tables are related to the building table.
`apartment` and `common_room` tables are related to the `building` table, as apartment is considered as residential living and common room is considered as commercial or public facility.
`apartment_owner` and `room` tables are related to the `apartment` table, as apartment owner is the owner of the apartment and room is the part of the apartment. Here I assume that apartment has not only one owner, that's why it needs a separate table to store the owner information, same as the room.

As the task mentions that "All Rooms should have ..." can also be interpreted as all rooms include just apartment and common room. But for my design, I have considered the room as a separate entity that is part of the apartment.

There is another assumption that if the building requested temperature has been changed, only common rooms will be affected, not the apartment rooms. Because residential owners can control their room temperature, but common rooms are controlled by the building management.

## Note

- All business logic APIs are authenticated with JWT token.