# smartsofttest
Описание:

При запуске приложения, необходимо получить список актуальных валют и их курсов с сайта ЦБРФ http://www.cbr.ru/scripts/XML_daily.asp (дополнительная информация https://cbr.ru/development/sxml/) и записать их в базу данных (индентификаторы, коды, названия), а так же курсы (привязанные к валюте) на дату запроса. В конвертере должна быть авторизация по логину и паролю. Пользователь пройдя авторизацию попадает на главный экран, где может выбрать из какой валюты и в какую будет конвертация. Указывает количество переводимых средств и нажимает кнопку "Конвертировать". После чего происходит запрос в БД на получение актуального курса на текущую дату, если данные на текущую дату отсутствуют, необходимо произвести получение курсов с сайта ЦБ и добавить новые записи в базу данных. Также в конвертере должна вестись история произведенных конвертаций с записью в базу данных со ссылкой на курс по которой была произведена конвертация. Историю можно посмотреть на той же странице конвертера или отдельной вкладке (возможна реализация базовых фильтров). Остальная функциональность и визуал по желанию.
