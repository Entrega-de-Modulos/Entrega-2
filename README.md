# Gateway JWT Demo

Arquitetura com três serviços: `gateway` (roteamento + validação JWT), `auth-service` (login/registro + emissão de token) e `protected-service` (endpoint que exige token).

## Por que
- Separar autenticação e roteamento.
- Fluxo stateless usando JWT (expira em 1h).
- Base de usuários em memória para facilitar estudo.

## Arquitetura (fluxo)
```
Gateway (:8080) -> /auth/* -> auth-service (gera JWT)
                           -> /protected/* -> protected-service (exige Bearer)
```
Segredo único via `JWT_SECRET` para assinar e validar tokens.

## Endpoints
Auth:
- POST /auth/login {email,password} -> {token}
- POST /auth/register {email,password} -> {token}

Protegido:
- GET /protected/ -> requer Authorization: Bearer <token>

## Rodando
```bash
docker compose up -d --build
```
Acesso: `http://localhost:8080`

## Teste Rápido
Registrar:
```bash
curl -X POST http://localhost:8080/auth/register -H 'Content-Type: application/json' \
	-d '{"email":"user@fatec.com","password":"senha123"}'
```
Login:
```bash
curl -X POST http://localhost:8080/auth/login -H 'Content-Type: application/json' \
	-d '{"email":"admin@fatec.com","password":"senha123"}'
```
Usar token:
```bash
TOKEN="<jwt>"; curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/protected/
```

## Variáveis
`JWT_SECRET` | `AUTH_SERVICE_URL` | `PROTECTED_SERVICE_URL`
