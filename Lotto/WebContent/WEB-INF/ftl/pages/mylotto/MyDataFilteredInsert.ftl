			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists} > MY로또 필터등록</span></#if></h1>
						${ex_count?if_exists}&nbsp;회차
					</div>
				</div>
				
				<!-- section -->
				<section class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<form id="writestep1-form" name="writestep1-form" class="smart-form client-form" >
						<fieldset>
							<div class="row">
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-1 left-pull">
									<label>회차</label>
									<label class="input">
										<input type="text" id="ex_count" name="ex_count" value="${ex_count?if_exists}"/>
									</label>
								</div>
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-1 left-pull">
									<label>번호1</label>
									<label class="input">
										<input type="text" id="num1" name="num1" />
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-1 left-pull">
									<label>번호2</label>
									<label class="input">
										<input type="text" id="num2" name="num2" />
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-1 left-pull">
									<label>번호3</label>
									<label class="input">
										<input type="text" id="num3" name="num3" />
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-1 left-pull">
									<label>번호4</label>
									<label class="input">
										<input type="text" id="num4" name="num4" />
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-1 left-pull">
									<label>번호5</label>
									<label class="input">
										<input type="text" id="num5" name="num5" />
									</label>
								</div>		
								<div class="form-group col-xs-12 col-sm-6 col-md-6 col-lg-1 left-pull">
									<label>번호6</label>
									<label class="input">
										<input type="text" id="num6" name="num6" />
									</label>
								</div>		
							</div>
						</fieldset>
					</form>
					
					<!-- row -->
					<div class="row">
						<div class="content">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<a href="javascript:save();" class="btn btn-labeled btn-success pull-left"> <span class="btn-label"><i class="fa fa-pencil"></i></span>저장 </a>
								<a href="javascript:cancel();" class="btn btn-labeled btn-default pull-left" style="margin-left: 10px;"> <span class="btn-label"><i class="fa fa-mail-reply"></i></span>취소 </a>							
							</div>
						</div>
					</div>				
				</section>									
				<!-- end section -->

			</div>
			<!-- END MAIN CONTENT -->
