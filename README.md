# Parcial Final Programación N-Capas – Seguridad con Spring Security + JWT

## Estudiantes
- **Nombre de estudiante 1:** Josué Fernando Gómez Guardado – 00103722
- **Nombre de estudiante 2:**  Paola Alexandra Pérez Pérez  – 00117222  
- **Sección:** 1  

endpoint en insomna: http://localhost:8080/auth/login

usuarios para usar en insomnia:
{
  "correo": "tech@soporte.com",
  "password": "techpass"
}

{
  "correo": "user@soporte.com",
  "password": "userpass"
}

> **Nota:** Por falta de tiempo no se completó la dockerización (Parte 5) ni la evidencia en Postman. Sin embargo, el proyecto es funcional localmente con al crear una base de datos en postgress con el nombre que esta en el codigo (tal como se habia colocado en el proyecto base).


## Requisitos implementados

1. **JWT Login**  
   - Endpoint **`POST /auth/login`**  
   - Valida credenciales contra la tabla `usuarios` en PostgreSQL  
   - Retorna un token JWT firmado con clave de 256 bits  

2. **Filtros y validación**  
   - Sección de seguridad en `SecurityConfig`  
   - `JwtAuthenticationFilter` que extrae el header `Authorization: Bearer <token>`  
   - Bypass completo para `/auth/**`  

3. **Protección de endpoints**  
   - Solo `/auth/login` permite peticiones sin token  
   - Todos los demás (`/api/users/**`, `/api/tickets/**`) requieren JWT válido  

4. **Roles y autorización**  
   - **USER**:  
     - `POST /api/tickets` → Crear tickets  
     - `GET /api/tickets/mine` → Ver solo sus tickets  
   - **TECH**:  
     - `GET /api/tickets` → Ver todos los tickets  
     - `PUT /api/tickets` → Actualizar estado de tickets  


