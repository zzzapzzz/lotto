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

					<!-- search condition -->
					<div class="well">
						<label class="col-md-1 control-label" for="brand">회차</label>
						<div class="col-lg-2">
							<select class="form-control" id="search_win_count">
							</select> 
						</div>
						<div id="search" class="btn btn-default btn-primary"><i class="fa fa-search"></i> 조회</div>
					</div>
					
					<span id="add" class="btn btn-success"><i class="fa fa-pencil"></i> 등록</span>
					<span id="modify" class="btn btn-warning"><i class="fa fa-edit"></i> 수정</span>
					<span id="del" class="btn btn-danger"><i class="fa fa-trash-o"></i> 삭제</span>
					<span id="uploadFile" class="btn btn-success"><i class="fa fa-upload"></i> Excel 업로드</span>
					<span id="winDataAnalysis" class="btn btn-info"><i class="fa fa-pencil"></i> 당첨번호 분석</span>
					
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
										<span>당첨번호목록 File Upload</span>
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
				    
					<!-- winDataInfoPopup -->
				    <div id="winDataInfoPopup" class="pop-layer-col2">
				        <div class="pop-container">
				            <div class="pop-conts">
				                <!--content //-->
				                <form action="" id="input-form" class="smart-form client-form">
									<header>
										<span id="winDataInfoTitle"></span>
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
															<label class="label">회차</label>
															<label class="input">
																<input type="text" id="win_count" name="win_count">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 회차를 입력하세요.</b>
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">번호1</label>
															<label class="input">
																<input type="text" id="num1" name="num1">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 번호1을 입력하세요.</b> 
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">번호2</label>
															<label class="input">
																<input type="text" id="num2" name="num2">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 번호2를 입력하세요.</b> 
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">번호3</label>
															<label class="input">
																<input type="text" id="num3" name="num3">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 번호3을 입력하세요.</b> 
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">번호4</label>
															<label class="input">
																<input type="text" id="num4" name="num4">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 번호4를 입력하세요.</b> 
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">번호5</label>
															<label class="input">
																<input type="text" id="num5" name="num5">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 번호5를 입력하세요.</b> 
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">번호6</label>
															<label class="input">
																<input type="text" id="num6" name="num6">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 번호6을 입력하세요.</b> 
															</label>
														</section>
													</td>
													<td>
														<section>
															<label class="label">보너스번호</label>
															<label class="input">
																<input type="text" id="bonus_num" name="bonus_num">
																<b class="tooltip tooltip-top-right"><i class="fa txt-color-teal"></i> 보너스번호를 입력하세요.</b> 
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
				    <!-- end winDataInfoPopup -->
				    
				</div>
				<!-- end dim-layer -->
				
			</div>
			<!-- END MAIN CONTENT -->

