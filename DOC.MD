# JMH Бенчмарки обращения к методу
***

## Целевая функция
Класс: `record Student(String name, String surname){}`

Метод: `Student.name()`

## Результаты бенчмарков

| Метод доступа     | Режим | Время(нс) |
|-------------------|-------|-----------|
| Прямой доступ     | avgt  | 0,459     |
| LambdaMetaFactory | avgt  | 0,939     |
| MethodHandlers    | avgt  | 4,962     |
| Reflection        | avgt  | 5,412     |
