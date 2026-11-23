# PHP Packagist Catalog — KMP

Кроссплатформенное приложение (Compose Multiplatform): каталог пакетов PHP из [Packagist](https://packagist.org).

**Предметная область:** каталог PHP-пакетов. Список пакетов с описанием, версией, количеством загрузок и звёзд; поиск по названию; избранное; экран детали пакета со списком зависимостей (`require`) и переходом по зависимостям в другой пакет.

**Данные (В1):** моки — 23 записи в форме API Packagist (`packagist.org/search.json`: `name`, `description`, `version`, `downloads`, `stars`, `require`), чтобы в В2 заменить моки сетью без переписывания экранов.

## Структура

- `shared` — общий модуль: модель/репозиторий/моки (`domain`, `data`), экраны и ViewModel (`presentation`), тема из токенов (`presentation/theme`), строки `ru`/`en` в compose-ресурсах с переключением языка в рантайме.
- `androidApp` — Android-приложение.
- `webApp` — веб-приложение (JS и WasmJS), поддержана история браузера.

## Запуск

- Android: `./gradlew :androidApp:assembleDebug`
- Web (Wasm): `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
- Web (JS): `./gradlew :webApp:jsBrowserDevelopmentRun`

## Тесты

- `./gradlew :shared:testAndroidHostTest`

## Тема и язык

Переключение светлой/тёмной/системной темы и языка RU/EN — кнопками в шапке приложения.
