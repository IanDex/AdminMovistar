<div class="modal fade" id="modal-small" data-backdrop="static" data-keyboard="false" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title pull-left">Cambiar Contraseña</h5>
            </div>
            <form id="changePassId">
                <div class="modal-body">

                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="zmdi zmdi-lock zmdi-hc-fw"></i></span>
                        </div>
                        <input type="password" id="passold" name="__pass" class="form-control " value="" placeholder="Tu contraseña actual">
                        <i class="form-group__bar"></i>
                    </div>

                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="zmdi zmdi-lock-outline zmdi-hc-fw"></i></span>
                        </div>
                        <input type="password" class="form-control" name="__pass" id="passnew" value="" placeholder="Tu nueva contraseña">
                        <i class="form-group__bar"></i>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="zmdi zmdi-lock-outline zmdi-hc-fw"></i></span>
                        </div>
                        <input type="password" class="form-control" name="__pass" id="passnew2" value="" placeholder="Confirma tu contraseña">
                        <i class="form-group__bar"></i>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="checkpass">
                        <label class="custom-control-label" for="checkpass">Mostrar Contraseña</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-link" id="btn_cancel" data-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-link" id="btn_pass" disabled>Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" data-backdrop="static" data-keyboard="false" id="exitPass" tabindex="-1">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title pull-left">Cambios detectados</h5>
            </div>
            <div class="modal-body">
                Acabas de cambiar la contraseña por favor vuelve a iniciar sesión
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link"><a href="/logout">Ok</a></button>
            </div>
        </div>
    </div>
</div>

<!-- Crear Usuario Admin -->

<div data-backdrop="static" data-keyboard="false" class="modal fade" id="create" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content" style="height: auto;">
                    <div class="modal-header" style="margin-bottom:-30px;">
                        <div class="form-group">
                            <h5 class="h2">Crear </h5>
                            <span id="error-create">Los campos en rojo son Obligatorios</span>
                        </div>
                        <div class="btn-group btn-group-toggle" data-toggle="buttons">
                            <label class="btn active">
                                <input type="radio" name="options" id="user" autocomplete="off" checked value="user">
                                Usuario
                            </label>
                            <label class="btn">
                                <input type="radio" name="options" id="admin" autocomplete="off" value="admin"> Admin
                            </label>
                        </div>

                    </div>
                    <div class="modal-body">

                        <form id="create-user">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Nombres" id="name" name="createUser">
                                <i class="form-group__bar"></i>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Correo" name="createUser" id="email">
                                <i class="form-group__bar"></i>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Cargo" name="createUser" id="cargo">
                                <i class="form-group__bar"></i>
                            </div>
                            <div class="form-group">

                                <select class="select2" id="permisos" name="createUser" multiple data-placeholder="Permisos"
                                    style="width: 150%">
                                </select>
                                <span><strong>Mantén presionado Ctrl. para seleccionar varios</strong></span>
                            </div>

                        </form>

                        <form id="create-admin">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Correo" name="createAdmin" id="correo">
                                <i class="form-group__bar"></i>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Nombres" name="createAdmin" id="nombre">
                                <i class="form-group__bar"></i>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Usuario" name="createAdmin" id="usuario">
                                <i class="form-group__bar"></i>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-link" data-dismiss="modal" id="btn_cancel_create">Cancelar</button>
                        <button type="button" class="btn btn-link" disabled="disabled" id="btn_create">Guardar</button>
                    </div>
                </div>
            </div>
        </div>
        
<!-- Modal Base de datos  -->
                        <div class="modal fade" id="data-base" data-backdrop="static" data-keyboard="false" tabindex="-1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <div class="toolbar">
                                        <div class="toolbar__label">Base de datos</div>
					                        <div class="actions">
					                            <i class="actions__item zmdi zmdi-search" data-ma-action="toolbar-search-open"></i>
					                        </div>
					
					                        <div class="toolbar__search onkeyup" style="display: none;">
					                            <input id="find_table" type="text" placeholder="Buscar...">
					                            <i class="toolbar__search__close zmdi zmdi-long-arrow-left" data-ma-action="toolbar-search-close"></i>
					                        </div>
					                    </div>
                                        </div>
                                        <div class="modal-body modal-scroll">
                                        
                                            <ul class="list list--check" id="show--tables">
											    
											</ul>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn_ok btn btn-link" data-dismiss="modal">Ok</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

