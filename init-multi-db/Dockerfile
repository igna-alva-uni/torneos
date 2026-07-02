FROM postgres:15.18-trixie
# Copiar todos los archivos .sql de inicialización al directorio entrypoint de Postgres
# Al iniciar el contenedor por primera vez, Postgres ejecutará todos los scripts .sql en orden alfabético
COPY *.sql /docker-entrypoint-initdb.d/
