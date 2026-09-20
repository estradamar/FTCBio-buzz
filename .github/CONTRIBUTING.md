# Contribution Guidelines – FTCBio-buzz

Welcome to the **FTCBio-buzz** repository! To maintain strict control over our robot's codebase and ensure stability across every testing phase and competition, we follow a hierarchical branching model with protected branches and mandatory Pull Request (PR) reviews.

---

## 1. Branch Hierarchy

Core branches are protected against direct changes (*direct pushes are disabled*). No one is allowed to push code directly or merge without an authorized Pull Request.

The lifecycle of code advances strictly in the following order:

```
master / main (Competition)
  ▲
  │  [PR approved by Admin + Coach Approval]
UAT (User Acceptance Test)
  ▲
  │  [PR approved by Admin + Successful physical robot tests]
SIT (System Integration Test)
  ▲
  │  [PR approved by Admin]
develop (Development Integration)
  ▲
  │  [PR approved by Admin]
feature/<username>/<description> (Individual development)
```

### Branch Descriptions

* **`master` / `main` (Production / Competition):**
    * The final, battle-tested build loaded onto the Control Hub / Driver Station during official tournament matches.
* **`UAT` (User Acceptance Test):**
    * Code validated and formally approved by team coaches and mentors.
* **`SIT` (System Integration Test):**
    * Code that has been physically deployed and verified on the actual robot hardware.
* **`develop`:**
    * Active integration branch where different components and modules are combined and tested prior to full hardware integration.
* **`feature/...` branches:**
    * Daily working branches where individual team members develop specific modules or bug fixes.

---

## 2. Branch Naming Conventions

All new work branches must branch off **`develop`** and adhere to the following naming pattern:

```text
feature/<username>/<module-or-description><optional-version>
```

### Rules:

1. **`<username>`:** Your name or GitHub handle (e.g., `mar`).
2. **`<module-or-description>`:** A concise label describing what is being developed (e.g., `camara`, `telem`, `vision`).
3. **Version / Iteration (optional but recommended):** A numeric suffix to distinguish iterations (e.g., `00`, `07`).

### Valid Examples:

* `feature/mar/vision07`
* `feature/mar/camara`
* `feature/mar/telem`

---

## 3. Step-by-Step Workflow

### Step 1: Create your local branch
Ensure your local `develop` branch is up to date before branching off:

```bash
git checkout develop
git pull origin develop
git checkout -b feature/your-username/feature-name
```

### Step 2: Develop and test individually
Commit your changes with clear, descriptive messages:

```bash
git add .
git commit -m "feat(vision): initial AprilTag pipeline calibration"
git push origin feature/your-username/feature-name
```

### Step 3: Pull Request into `develop`
* Once your feature is working locally/in your unit tests, open a **Pull Request targeting `develop`**.
* **Requirement:** Merging directly into `develop` is strictly prohibited. Every PR requires review and authorization from the **Repository Administrator**.

### Step 4: Promotion to `SIT` (System Integration Test)
* Once the components are integrated into `develop` and ready for full hardware verification, a **PR from `develop` to `SIT`** is created.
* The build must be loaded onto the physical robot and thoroughly tested.
* Requires approval by the **Repository Administrator**.

### Step 5: Promotion to `UAT` (User Acceptance Test)
* Once physical testing on `SIT` is successful, open a **PR from `SIT` to `UAT`**.
* Requires formal review and sign-off from the **coaches/mentors** along with **Admin** authorization.

### Step 6: Deployment to `master` (Competition)
* Prior to official tournament matches, verified code from `UAT` is promoted to `master` via an administrative Pull Request.

---

## 4. Pull Request (PR) Rules

1. **Descriptive Title:** Clearly indicate the module and purpose (e.g., `feat(vision): AprilTag detection pipeline integration`).
2. **Detailed Description:**
    * What was added, modified, or fixed?
    * How was it tested (simulator, bench test, or robot chassis)?
3. **No Self-Merging:** Merging your own PR into protected branches is forbidden.
4. **Keep Branches Synchronized:** If new commits landed in `develop`, perform a `git rebase develop` or `git merge develop` on your feature branch before requesting review.

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