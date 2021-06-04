# TPA

## Entrega 1
### Diagrama de clases

[Diagrama](https://lucid.app/documents/view/372f27ea-7637-4845-92ed-b14d86ed43ea)

## Entrega 2
### Diagrama de clases

[Diagrama](https://lucid.app/lucidchart/d25b4587-01dc-4195-a0fa-87a428fd347f/edit?page=0_0#)


# Validar el proyecto de forma exahustiva

```
mvn clean verify
```

Este comando hará lo siguiente:

 1. Ejecutará los tests
 2. Validará las convenciones de formato mediante checkstyle
 3. Detectará la presencia de (ciertos) code smells
 4. Validará la cobertura del proyecto

# Entrega del proyecto

Para entregar el proyecto, crear un tag llamado `entrega-final`. Es importante que antes de realizarlo se corra la validación
explicada en el punto anterior. Se recomienda hacerlo de la siguiente forma:

```
mvn clean verify && git tag entrega-final && git push origin HEAD --tags
```
