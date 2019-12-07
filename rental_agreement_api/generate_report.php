<?php
    include 'mysqlconfig.php';
    include 'fpdf/fpdf.php';

    if($_SERVER['REQUEST_METHOD']=='POST'){

        $usrID = intval($_POST['usrId']);
        $agmID = intval($_POST['agmId']);

  
        class PDF extends FPDF {

            function Header(){
                $this->SetFont('Arial','B',15);
                
                //dummy cell to put logo
                //$this->Cell(12,0,'',0,0);
                //is equivalent to:
                $this->Cell(12);
                
                //put logo
                $this->Image('contract.png',10,10,10);
                
                $this->Cell(100,10,'MyAgreement',0,1);
                
                //dummy cell to give line spacing
                //$this->Cell(0,5,'',0,1);
                //is equivalent to:
                $this->Ln(5);
                
            }
         
            function Footer(){    
                //Go to 1.5 cm from bottom
                $this->SetY(-1);
                        
                $this->SetFont('Arial','',8);
                
                //width = 0 means the cell is extended up to the right margin
                $this->Cell(0,10,'Page '.$this->PageNo()." / {pages}",0,0,'C');
            }

        }




        $sql = mysqli_query($conn, "Select 
                                        h.agm_id, h.agm_token_id, h.agm_id,  h.agm_prop_id, 
                                        a.user_id, a.user_name, a.user_type, 
                                        b.prop_id, b.prop_serv_type, b.prop_bldg, b.prop_plotno, b.prop_floorno, b.prop_road, b.prop_area, b.prop_suburban, b.prop_city, b.prop_taluka, b.prop_state, b.prop_pincode, b.prop_type, b.prop_flat_area, 
                                        c.own_fname, c.own_mname, c.own_lname, c.own_age, c.own_gender, c.own_mobno, c.own_prof, c.own_panno, c.own_aadharno, c.own_bldg, c.own_plot, c.own_floorno, c.own_road,  c.own_area,  c.own_suburban,  c.own_city, c.own_taluka,  c.own_state,  c.own_pcode,  c.own_nocoowner,  c.own_stat, 
                                        d.tent_fname, d.tent_mname, d.tent_lname, d.tent_age, d.tent_gender, d.tent_mobno, d.tent_prof,  d.tent_panno, d.tent_aadharno, d.tent_bldg, d.tent_plotno, d.tent_floorno, d.tent_road, d.tent_area, d.tent_suburban,  d.tent_city,  d.tent_taluka,  d.tent_state,  d.tent_pcode,  d.tent_notenant, 
                                        e.wit1_name, e.wit1_age, e.wit1_gender, e.wit1_mobno, e.wit1_prof, e.wit1_panno, e.wit1_aadhar,  e.wit1_add1, e.wit1_add2,  e.wit2_name, e.wit2_age, e.wit2_gender, e.wit2_mobno, e.wit2_prof, e.wit2_panno, e.wit2_aadhar, e.wit2_add1, e.wit2_add2,  
                                        f.prop_pay_period, f.prop_pay_deposit, f.prop_pay_var_mnth_per, f.prop_pay_rent_type,  f.prop_pay_rent_fixed_amt, f.prop_pay_dep_stat, f.prop_pay_dep_stat_mode,  f.prop_pay_bank_name, f.prop_pay_branch_name, f.prop_pay_chk_dt, f.prop_pay_chk_no, f.prop_pay_bank_acc_no, f.prop_pay_agr_start_dt, f.prop_pay_agr_end_dt, f.prop_pay_cr_srv
                                    from  
                                        user_mast a,  
                                        prop_mast b,  
                                        own_mast c,  
                                        tent_mast d, 
                                        wit_mast e, 
                                        prop_pay_trans f,  
                                        rent_serv_trans g,  
                                        agree_mast h
                                    where
                                        a.user_id = " . $usrID . " and
                                        h.agm_id = " . $agmID ." and
                                        a.user_id = b.prop_user_id and 
                                        b.prop_id = c.own_prop_id and 
                                        b.prop_id = d.tent_prop_id and 
                                        b.prop_id = e.wit_prop_id and 
                                        b.prop_id = f.prop_pay_prop_id and 
                                        b.prop_id = g.rent_trans_prop_id and 
                                        b.prop_id = h.agm_prop_id and 
                                        a.user_id = h.agm_user_id and
                                        b.prop_stat = 1 and 
                                        c.own_stat = 1 and
                                        d.tent_stat = 1 and 
                                        e.wit_stat = 1 and 
                                        f.prop_pay_stat = 1 and 
                                        g.rent_trans_stat = 1 
                                        order by a.user_id, a.user_type");
    
        $agree = mysqli_fetch_array($sql);


        $pdf = new FPDF('P','mm','A4');
        $pdf->AliasNbPages('{pages}');
        $pdf->AddPage();

        /***************************** Header *****************************************/

        //set font to arial,bold,14pt
        $pdf->SetFont('Arial','B',16);

        //Cell(width , height , text , border , end line , [align] )
        $pdf->Image('contract.png',70,10,10);
        $pdf->Cell(189  ,10,'MyAgreement',0,0,'C');//end of line

        $pdf->ln();
        $pdf->ln();



        //set font to arial, regular, 12pt
        $pdf->SetFont('Arial','',12);

        $pdf->Cell(130  ,5,'[Street Address]',0,0);
        $pdf->Cell(59   ,5,'',0,1);//end of line

        $pdf->Cell(130  ,5,'[City, Country, ZIP]',0,0);
        $pdf->Cell(25   ,5,'Date: ',0,0);
        $pdf->Cell(34   ,5,'[dd/mm/yyyy]',0,1);//end of line

        $pdf->Cell(130  ,5,'Phone [+12345678]',0,0);
        $pdf->Cell(25   ,5,'Agrement ID: ',0,0);
        $pdf->Cell(34   ,5,$agree['agm_id'],0,1);//end of line

        $pdf->Cell(130  ,5,'Fax [+12345678]',0,0);  
        $pdf->Cell(25   ,5,'Property ID: ',0,0);
        $pdf->Cell(34   ,5,$agree['agm_prop_id'],0,1);//end of line

        $pdf->Cell(189  ,3,'',0,1);
        $pdf->Cell(189  ,0.2,'',1,0);
        //make a dummy empty cell as a vertical spacer
        $pdf->Cell(189  ,10,'',0,1);//end of line

        /*//make a dummy empty cell as a vertical spacer
        $pdf->Cell(189  ,10,'',0,1);//end of line*/

        /***************************** Property Details *****************************************/

        //set font to arial,bold,14pt
        $pdf->SetFont('Arial','B',14);

        $pdf->Cell(189  ,5,'Property Details',0,0,'C');//end of line

        $pdf->ln();
        $pdf->ln();

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Name of Bldg/Apt',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_bldg'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Flat No/Plot No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_plotno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Floor No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_floorno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Road',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_road'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Area',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_area'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Suburban',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_suburban'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'City',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_city'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Taluka',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_taluka'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'State',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_state'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Pin Code',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pincode'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Property Type',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_type'],1,1);


        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Property Area',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_flat_area'],1,1);
        $pdf->ln();

        /***************************** Owner Details*****************************************/

        //set font to arial,bold,14pt
        $pdf->SetFont('Arial','B',14);

        $pdf->Cell(189  ,5,'Owner Details',0,0,'C');//end of line

        $pdf->ln();
        $pdf->ln();

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'First Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_fname'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Middle Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_mname'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Last Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_lname'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'DOB',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_age'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Gender',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_gender'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Mobile No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_mobno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Profession',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_prof'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Pan Card No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_panno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Aadhar Card No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_aadharno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Name of Bldg/Apt',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_bldg'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Flat No/Plot No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_plot'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Floor No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_floorno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Road',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_road'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Area',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_area'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Suburban',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_suburban'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'City',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_city'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Taluka',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_taluka'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'State',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_stat'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Pin Code',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_pcode'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'No. of Co-Owner',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['own_nocoowner'],1,1);
        $pdf->ln();

        /***************************** Tenant Details*****************************************/

        //set font to arial,bold,14pt
        $pdf->SetFont('Arial','B',14);

        $pdf->Cell(189  ,5,'Tenant Details',0,0,'C');//end of line

        $pdf->ln();
        $pdf->ln();

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'First Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_fname'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Middle Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_mname'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Last Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_lname'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'DOB',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_age'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Gender',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_gender'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Mobile No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_mobno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Profession',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_prof'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Pan Card No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_panno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Aadhar Card No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_panno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Name of Bldg/Apt',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_bldg'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Flat No/Plot No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_plotno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Floor No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_floorno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Road',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_road'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Area',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_area'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Suburban',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_suburban'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'City',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_city'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Taluka',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_taluka'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'State',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_state'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Pin Code',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_pcode'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'No. of Co-Tenant',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['tent_notenant'],1,1);
        $pdf->ln();

        /***************************** Withness-1 Details*****************************************/

        //set font to arial,bold,14pt
        $pdf->SetFont('Arial','B',14);

        $pdf->Cell(189  ,5,'Withness-1 Details',0,0,'C');//end of line

        $pdf->ln();
        $pdf->ln();

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit1_name'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'DOB',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit1_age'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Gender',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit1_gender'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Mobile No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit1_mobno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Profession',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit1_prof'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Pan Card No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit1_panno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Aadhar Card No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit1_aadhar'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Address',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit1_add1'],1,1);
        $pdf->Cell(60  ,7,"",1,0);
        $pdf->Cell(129   ,7,$agree['wit1_add2'],1,1);
        $pdf->ln();

        /***************************** Withness-2 Details*****************************************/

        //set font to arial,bold,14pt
        $pdf->SetFont('Arial','B',14);

        $pdf->Cell(189  ,5,'Withness-2 Details',0,0,'C');//end of line

        $pdf->ln();
        $pdf->ln();

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit2_name'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'DOB',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit2_age'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Gender',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit2_gender'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Mobile No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit2_mobno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Profession',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit2_prof'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Pan Card No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit2_panno'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Aadhar Card No',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit2_aadhar'],1,1);


        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Address',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['wit2_add1'],1,1);
        $pdf->Cell(60  ,7,"",1,0);
        $pdf->Cell(129   ,7,$agree['wit2_add2'],1,1);

        $pdf->ln();

        /***************************** Payment Details*****************************************/

        //set font to arial,bold,14pt
        $pdf->SetFont('Arial','B',14);

        $pdf->Cell(189  ,5,'Payment Details',0,0,'C');//end of line

        $pdf->ln();
        $pdf->ln();

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Rent Duration (In Months)',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_period'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Deposit Amount',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_deposit'],1,1);


        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Monthly Rent Amount',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,"",1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Is Deposit Amount Paid',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_dep_stat'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Mode of Payment',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_dep_stat_mode'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Bank Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_bank_name'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Branch Name',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_branch_name'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Cheque Date',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_chk_dt'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Cheque Amount',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,"",1,1);    ///Needs edit

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Rent Type',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_rent_type'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Fixed Amount',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_rent_fixed_amt'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Variable Amount',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_var_mnth_per'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Agrement Start Date',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_agr_start_dt'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'Agrement End Date',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_agr_end_dt'],1,1);

        $pdf->SetFont('Arial','B',12);
        $pdf->Cell(60  ,7,'To Mail Document',1,0);
        $pdf->SetFont('Arial','',12);  
        $pdf->Cell(129   ,7,$agree['prop_pay_cr_srv'],1,1);

        //PDF file will save in ( 'report/' directory ) 
        $dir = "report/";
        $file_name = $agmID . ".pdf";

        $pdf->Output($dir . "" .$file_name, 'F', true);

        echo "generated";

    }

?>