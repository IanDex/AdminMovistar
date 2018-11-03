<header class="header">
    <div class="navigation-trigger" data-ma-action="aside-open" data-ma-target=".sidebar">
        <div class="navigation-trigger__inner">
            <i class="navigation-trigger__line"></i>
            <i class="navigation-trigger__line"></i>
            <i class="navigation-trigger__line"></i>
        </div>
    </div>

    <div class="header__logo hidden-sm-down">
        <h1><a href="#">Administrador </a></h1>

    </div>

    <form class="search">
        <div class="search__inner">
            <input autocomplete="off" type="text" class="search__text" id="searchGlobal" placeholder="Search for people, files, documents...">
            <i class="zmdi zmdi-search search__helper" data-ma-action="search-close"></i>
        </div>
    </form>

    <ul class="top-nav">
        <li class="hidden-xl-up"><a href="#" data-ma-action="search-open"><i class="zmdi zmdi-search"></i></a></li>

        <li class="dropdown hidden-xs-down">
            <a href="#" data-toggle="dropdown"><i class="zmdi zmdi-more-vert"></i></a>

            <div class="dropdown-menu dropdown-menu-right">
                <div class="dropdown-item theme-switch">
                    Theme Switch

                    <div class="btn-group btn-group-toggle btn-group--colors" data-toggle="buttons">
                        <label class="btn bg-green active"><input type="radio" value="green" autocomplete="off" checked></label>
                        <label class="btn bg-blue"><input type="radio" value="blue" autocomplete="off"></label>
                        <label class="btn bg-red"><input type="radio" value="red" autocomplete="off"></label>
                        <label class="btn bg-orange"><input type="radio" value="orange" autocomplete="off"></label>
                        <label class="btn bg-teal"><input type="radio" value="teal" autocomplete="off"></label>

                        <div class="clearfix mt-2"></div>

                        <label class="btn bg-cyan"><input type="radio" value="cyan" autocomplete="off"></label>
                        <label class="btn bg-blue-grey"><input type="radio" value="blue-grey" autocomplete="off"></label>
                        <label class="btn bg-purple"><input type="radio" value="purple" autocomplete="off"></label>
                        <label class="btn bg-indigo"><input type="radio" value="indigo" autocomplete="off"></label>
                        <label class="btn bg-brown"><input type="radio" value="brown" autocomplete="off"></label>
                    </div>
                </div>
            </div>
        </li>

        <li class="hidden-xs-down">
            <a href="#" title="Actualizar" data-toggle="tooltip" data-placement="bottom" onclick="$('#fechaInicio').val(null);$('#fechaFin').val(null);listar($('#intervaloDatatable').val(), '', '', '', '');">
                <i class="zmdi zmdi-refresh-alt"></i>
            </a>
        </li>

        <li class="dropdown hidden-xs-down">
            <div class="user__info" data-toggle="dropdown">
                <i class="avatar-char bg-red">C</i>
            </div>

            <div class="dropdown-menu">
                <a href="#" data-toggle="modal" data-target="#modal-small" class="dropdown-item">Cambiar Contraseña</a>
                <a href="/logout" class="dropdown-item">Salir</a>
            </div>
        </li>

    </ul>
</header>

<aside class="sidebar sidebar--hidden">
    <div class="scrollbar-inner">

        <ul class="navigation">
            <li><a href=""><i class="zmdi zmdi-home"></i> Opcion .1</a></li>

            <li><a href=""><i class="zmdi zmdi-format-underlined"></i> Opcion 2</a></li>

        </ul>
    </div>
</aside>