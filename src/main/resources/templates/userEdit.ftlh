<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Редактор пользователей</title>
</head>
<body>
    <h1>Редактор пользователей</h1>

    <form method="post" action="/user">
        <input type="text" name="newUsername" value="${user.username}">

        <#list roles as role>
            <div>
                <label>
                    <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                    ${role}
                </label>
            </div>
        </#list>

        <input type="hidden" name="userId" value="${user.id}">
        <button type="submit"> Сохранить изменения</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>

</body>
</html>