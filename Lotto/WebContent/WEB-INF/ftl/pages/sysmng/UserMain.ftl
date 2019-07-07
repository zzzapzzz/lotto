			<!-- MAIN CONTENT -->
			<div id="content">
				<input type="hidden" id="isPlugin" value="Y" />

				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa ${CurrMenuInfo.lna_element?if_exists}"></i> ${CurrMenuInfo.p_menu_nm?if_exists} <#if CurrMenuInfo.p_menu_id != 0> <span>> ${CurrMenuInfo.menu_nm?if_exists}</span></#if></h1>
					</div>
				</div>
				
				<!-- widget grid -->
				<section id="widget-grid" class="">

					<div class="well">
						<label class="col-md-1 control-label" for="searchKey">이름</label>
						<div class="col-lg-2">
							<input type="text" class="form-control" id="searchKey" placeholder="Search..." />
							<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i>이름 또는 이메일을 입력하세요.</b>							
						</div>
						<span id="search" class="btn btn-default btn-primary"><i class="fa fa-search"></i> 조회</span>
					</div>
					
					
					<span id="add" class="btn btn-success"><i class="fa fa-pencil"></i> 등록</span>
					<span id="modify" class="btn btn-warning"><i class="fa fa-edit"></i> 수정</span>
					<span id="del" class="btn btn-danger"><i class="fa fa-trash-o"></i> 삭제</span>
					<span id="thwdInit" class="btn btn-info"><i class="fa fa-refresh"></i> 비밀번호 초기화</span>
					<span id="uploadFile" class="btn btn-success"><i class="fa fa-upload"></i> Excel 업로드</span>
					<span id="downloadFile" class="btn btn-link pull-right"><i class="fa fa-download"></i> 양식 다운로드</span>
					
					<!-- row -->
					<div class="row">

						<!-- NEW WIDGET START -->
						<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

							<table id="jqgrid"></table>
							<div id="pjqgrid"></div>

						</article>
						<!-- WIDGET END -->

					</div>

					<!-- end row -->

				</section>
				<!-- end widget grid -->
				
				<!-- dim-layer -->
				<div class="dim-layer">
				    <div class="dimBg"></div>
				    
					<!-- uploadPopup -->
				    <div id="uploadPopup" class="pop-layer">
				    	<div class="pop-container">
				    		<div class="pop-conts">
				                <!--content //-->
				                <form action="" method="post" id="uploadfile-form" class="smart-form client-form" enctype="multipart/form-data">
				                	<header>
										<span>사용자목록 File Upload</span>
										<a href="#" class="btn-layerClose pull-right">
										X
										</a>
									</header>
									
									<fieldset>
										<section>
											<label class="label">Excel File</label>
											<label class="input">
												<input type="file" id="excel" name="excel" class="file" 
													multiple data-show-upload="false" data-show-caption="true"
													accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
												<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 파일을 등록하세요.</b>
											</label>
										</section>
									</fieldset>
									
									<footer>
										<button type="submit" class="btn btn-primary">
											저장
										</button>
										<button id="btnUploadClose" class="btn btn-default">
											취소
										</button>
									</footer>	
				                </form>
				                
				                <div class="progress">
    								<div class="bar"></div >
    								<div class="percent">0%</div >
								</div>
								<div id="status"></div>
				    		</div>
				    	</div>
				    </div>
					<!-- end uploadPopup -->
				    
					<!-- userInfoPopup -->
				    <div id="userInfoPopup" class="pop-layer-col2">
				        <div class="pop-container">
				            <div class="pop-conts">
				                <!--content //-->
				                <form action="" id="userinput-form" class="smart-form client-form">
									<header>
										<span id="userInfoTitle"></span>
										<a href="#" class="btn-layerClose pull-right">
										X
										</a>
									</header>
	
									<fieldset>
										<table class="table table-bordered">
											<tbody>
												<tr>
													<td>
														<section>
															<label class="label">사원ID</label>
															<label class="input">
																<input type="text" id="usr_id" name="usr_id">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 사번ID를 입력하세요.</b>
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">사원명</label>
															<label class="input">
																<input type="text" id="usr_nm" name="usr_nm">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 사원명을 입력하세요.</b> 
															</label>
														</section>
													</td>
												</tr>
												<tr>
													<td>
														<section>
															<label class="label">소속구분</label>
															<label class="select">
																<select id="store_type" name="store_type">
																</select> <i></i>
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">소속</label>
															<label class="input">
																<input type="text" id="store_desc" name="store_desc">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 소속을 입력하세요.</b>
															</label>
														</section>
													</td>
												</tr>
												<tr>
													<td>
													<section>
														<label class="label">Cost Centre</label>
														<label class="input">
															<input type="text" id="cost_centre_code" name="cost_centre_code">
															<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> Cost Centre를 입력하세요.</b>
														</label>
													</section>
													</td>
													<td>
														<section>
															<label class="label">Ship To</label>
															<label class="input">
																<input type="text" id="ship_to_code" name="ship_to_code">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> Ship To를 입력하세요.</b>
															</label>
														</section>
													</td>
												</tr>
												<tr>
													<td>
														<section>
															<label class="label">업무권한</label>
															<label class="select">
																<select id="auth_task" name="auth_task">
																</select> <i></i>
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">메뉴권한</label>
															<label class="select">
																<select id="auth_menu" name="auth_menu">
																</select> <i></i>
															</label>
														</section>
													</td>
												</tr>
												<tr>
													<td>
														<section>
															<label class="label">비밀번호</label>
															<label class="input">
																<input type="password" id="new_usr_thwd" name="new_usr_thwd">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 비밀번호를 입력하세요.</b>
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">비밀번호 확인</label>
															<label class="input">
																<input type="password" id="confirm_usr_thwd" name="confirm_usr_thwd">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 비밀번호를 다시 한 번 입력하세요.</b>
															</label>
														</section>
													</td>
												</tr>
												<tr>
													<td colspan="2">
														<section>
															<label class="label">사용여부</label>
															<label class="select">
																<select id="use_yn" name="use_yn">
																	<option value="Y">사용</option>
																	<option value="N">미사용</option>
																</select> <i></i>
															</label>
														</section>
													</td>
												</tr>
											</tbody>
										</table>
										
										
										
										
										
										
										
										
										
										
	
									</fieldset>
									<footer>
										<button type="submit" class="btn btn-primary">
											저장
										</button>
										<button id="btnClose" class="btn btn-default">
											취소
										</button>
									</footer>
								</form>
				                <!--// content-->
				            </div>
				        </div>
				    </div>
				    <!-- end userInfoPopup -->
				    
				</div>
				<!-- end dim-layer -->
			</div>
			<!-- END MAIN CONTENT -->

