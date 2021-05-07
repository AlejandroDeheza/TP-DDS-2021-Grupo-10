# TPA - Entrega 1

## Diagrama de clases

[Diagrama](https://lucid.app/lucidchart/invitations/accept/inv_4c8076c6-f097-4415-9ee2-bedfc32bf15f?viewport_loc=-1413%2C-2440%2C4779%2C2300%2C0_0)

## Explicacion




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
