package com.ozguryazilim.tekir.recruit.applicant;

import com.ozguryazilim.tekir.recruit.config.RecruitPages;
import com.ozguryazilim.telve.forms.SubView;
import com.ozguryazilim.telve.forms.SubViewPageBase;

/**
 *
 * @author oyas
 */
@SubView(containerPage = RecruitPages.ApplicantPages.ApplicantView.class, viewPage = RecruitPages.ApplicantPages.DocumentSubView.class, permission = "applicantDocument", order = 42)
public class ApplicantDocumentSubView extends SubViewPageBase{
    @Override
    public void reload() {
        //Şimdilik ne yapılacak bilmiyorum
    }
}
