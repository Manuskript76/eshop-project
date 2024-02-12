# Проект интернет-магазин

Интернет-магазин - веб-приложение, которое используется ежедневно миллионами пользователей по всему миру для того, чтобы просто и быстро совершать любые покупки.

В данном приложении на данный момент добавлено 5 сущностей: 

- Client - непосредственно покупатель
- ClientOrder - заказ клиента
- OrderProduct - товары, находящиеся в заказе 
- Product - товар
- Review - отзыв покупателя о каком-то продукте

### Ограничения:

1. Пользователь не сможет оформить заказ, если на складе недостаточно товаров выбранного типа

### Действия по добавлению:

1. Добавить в систему нового пользователя
2. Добавить в систему новый товар
3. Оформить заказ для выбранного пользователя: с добавлением товаров/выбором их количества.  
4. Добавить отзыв на товар 
  
### Действия по просмотру:

1. Просмотреть список всех пользователей системы.
2. Просмотреть список всех заказов выбранного пользователя.
3. Просмотреть список всех товаров в системе.
4. Просмотреть список всех заказов, в которые включен выбранный товар
5. Удалить заказы, сделанные раньше введённой даты.
6. Просмотреть все отзывы по данному товару 
7. Посмотреть все отзывы выбранного пользователя

### Схема базы данных

![image](https://github.com/Manuskript76/eshop-project/assets/154013820/41fbe800-315c-48d9-9325-6070924eb34f)