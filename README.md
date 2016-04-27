# 1516-2-LI41N-G11

## TODO Phase 2

#### Headers

1. Add headers to request &#10003;
2. Criar uma view para html de todas as entidades.
3. Implementar as views de forma a poder serem vistas no standard output ou num ficheiro, ou qualquer outra coisa
<br>**DEFAULT:** accept:text/html e file-name:standard+output</br>

#### Collections
1. Adicionar sql para collections &#10003;

#### Paginamento
1. Suportado por **GET /movies** e **GET /collections**
2. top=length e skip=indice inicial
<br>**DEFAULT:** top=full length e skip=0</br>

#### Sorting
1. Suportado por **GET /movies**
2. sortBy=... várias maneiras ascendentes e descendentes

#### Comandos
1. **OPTION/** - presents a list of available commands and their characteristics.
2. **EXIT/** - ends the application.
3. **POST /colletions name=?&description=?** - creates a new collection and returns its identifier &#10003;
4. **GET /collections** - returns the list of collections, using the insertion order. &#10003;
5. **GET /collections/{cid}** - returns the details for the cid collection, namely all the movies in that collection. &#10003;
6. **POST /collections/{cid}/movies** - adds a movie to the cid collection &#10003;
7. **DELETE /collections/{cid}/movies/{mid}** - removes the movie mid from the collections cid. &#10003;
