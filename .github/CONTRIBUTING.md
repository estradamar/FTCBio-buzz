# Guía de Contribución – FTCBio-buzz

¡Bienvenidos al repositorio de **FTCBio-buzz**! Para mantener un control estricto del código del robot y asegurar la estabilidad en cada etapa de prueba y competencia, seguimos un flujo de trabajo jerárquico basado en ramas protegidas y revisiones obligatorias mediante Pull Requests (PR).

---

## 1. Jerarquía de Ramas

El repositorio cuenta con ramas protegidas contra cambios directos (*push directo bloqueado*). Nadie puede subir cambios ni hacer merge directamente sin un Pull Request autorizado.

El ciclo de vida del código avanza en el siguiente orden estricto:

```text
master / main (Competencia)
  ▲
  │  [PR aprobado por Admin + Aprobación de Coaches]
UAT (User Acceptance Test)
  ▲
  │  [PR aprobado por Admin + Pruebas físicas exitosas]
SIT (System Integration Test)
  ▲
  │  [PR aprobado por Admin]
develop (Integración de Desarrollo)
  ▲
  │  [PR aprobado por Admin]
feature/<usuario>/<descripcion> (Desarrollo individual)
```

### Descripción de Ramas

* **`master` / `main` (Producción / Competencia):**
    * Versión final y estable que se cargará en el Control Hub / Driver Station durante la competencia oficial.
* **`UAT` (User Acceptance Test):**
    * Código validado y aprobado formalmente por los coaches y mentores del equipo.
* **`SIT` (System Integration Test):**
    * Código que ya fue probado físicamente en el robot real y ha demostrado funcionar correctamente en hardware integrado.
* **`develop`:**
    * Rama de integración activa. Aquí convergen las diferentes partes del código en desarrollo antes de probarse en el robot completo.
* **Ramas `feature/...`:**
    * Ramas de trabajo diario de cada programador donde se implementan nuevas funciones o mejoras.

---

## 2. Convención de Nombres para Ramas

Todas las ramas de trabajo deben crearse a partir de la rama **`develop`** y seguir la siguiente nomenclatura:

```bash
feature/<usuario>/<descripcion-o-modulo><version-opcional>
```

### Reglas:
1. **`<usuario>`:** Tu nombre o alias de GitHub (ej. `mar`).
2. **`<descripcion-o-modulo>`:** Nombre corto que describa lo que estás programando (ej. `camara`, `telem`, `vision`).
3. **Versión o iteración (opcional pero recomendado):** Puedes agregar un número para distinguir versiones del feature (ej. `00`, `07`).

### Ejemplos válidos:
* `feature/mar/vision07`
* `feature/mar/camara`
* `feature/mar/telem`

---

## 3. Flujo de Trabajo Paso a Paso

### Paso 1: Crear tu rama local
Asegúrate de estar en `develop` actualizado antes de partir:
```bash
git checkout develop
git pull origin develop
git checkout -b feature/tu-usuario/nombre-feature
```

### Paso 2: Desarrollar y probar individualmente
Realiza tus commits describiendo claramente los cambios:
```bash
git add .
git commit -m "feat(vision): calibracion inicial de pipeline april tags"
git push origin feature/tu-usuario/nombre-feature
```

### Paso 3: Pull Request a `develop`
* Una vez que tu feature funcione de manera individual, abre un **Pull Request hacia `develop`**.
* **Requisito:** Ningún merge a `develop` es directo; debe ser revisado y autorizado por el **Administrador del repositorio**.

### Paso 4: Promoción a `SIT` (System Integration Test)
* Una vez que los módulos se hayan integrado en `develop` y estén listos para cargarse al robot físico, se genera un **PR de `develop` hacia `SIT`**.
* Debe probarse en el hardware del robot.
* Se requiere autorización del **Administrador del repositorio**.

### Paso 5: Promoción a `UAT` (User Acceptance Test)
* Una vez que las pruebas físicas en `SIT` sean exitosas, se crea un **PR de `SIT` hacia `UAT`**.
* Se requiere validación y visto bueno de los **coaches** y la aprobación del **Admin**.

### Paso 6: Despliegue a `master` (Competencia)
* Antes del torneo o evento oficial, el código en `UAT` se transfiere a `master` mediante Pull Request final aprobado por el Administrador.

---

## 4. Reglas de Pull Requests (PR)

1. **Título claro:** Indica brevemente el módulo y qué hace (ej. `feat(vision): integración de pipeline de detección`).
2. **Descripción completa:**
    * ¿Qué se agregó o corrigió?
    * ¿Cómo se probó (simulador, banco de pruebas o robot)?
3. **No self-merge:** Está prohibido mergear tus propios PRs a ramas protegidas.
4. **Mantén tu rama al día:** Si `develop` tiene cambios nuevos, haz `git rebase develop` o `git merge develop` en tu rama feature antes de solicitar revisión.