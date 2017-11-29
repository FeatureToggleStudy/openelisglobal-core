package us.mn.state.health.lims.samplebatchentry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import us.mn.state.health.lims.common.action.BaseActionForm;
import us.mn.state.health.lims.common.action.IActionConstants;
import us.mn.state.health.lims.common.formfields.FormFields;
import us.mn.state.health.lims.common.services.DisplayListService;
import us.mn.state.health.lims.common.services.SampleOrderService;
import us.mn.state.health.lims.common.services.DisplayListService.ListType;
import us.mn.state.health.lims.common.util.DateUtil;
import us.mn.state.health.lims.common.util.StringUtil;
import us.mn.state.health.lims.patient.action.bean.PatientManagementInfo;
import us.mn.state.health.lims.patient.action.bean.PatientSearch;
import us.mn.state.health.lims.sample.action.BaseSampleEntryAction;

public class SampleBatchEntrySetupAction extends BaseSampleEntryAction {

  @Override
  protected ActionForward performAction(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String forward = "success";

    request.getSession().setAttribute(IActionConstants.NEXT_DISABLED, IActionConstants.TRUE);
    BaseActionForm dynaForm = (BaseActionForm) form;
    dynaForm.initialize(mapping);

    SampleOrderService sampleOrderService = new SampleOrderService();
    PropertyUtils.setProperty(dynaForm, "sampleOrderItems",
            sampleOrderService.getSampleOrderItem());
    PropertyUtils.setProperty(dynaForm, "sampleTypes",
            DisplayListService.getList(ListType.SAMPLE_TYPE_ACTIVE));
    PropertyUtils.setProperty(dynaForm, "testSectionList",
            DisplayListService.getList(ListType.TEST_SECTION));
    PropertyUtils.setProperty(dynaForm, "currentDate", DateUtil.getCurrentDateAsText());
    PropertyUtils.setProperty(dynaForm, "currentTime", DateUtil.getCurrentTimeAsText());
    PropertyUtils.setProperty(dynaForm, "sampleOrderItems.receivedTime",
            DateUtil.getCurrentTimeAsText());

    addProjectList(dynaForm);

    if (FormFields.getInstance().useField(FormFields.Field.InitialSampleCondition)) {
      PropertyUtils.setProperty(dynaForm, "initialSampleConditionList",
              DisplayListService.getList(ListType.INITIAL_SAMPLE_CONDITION));
    }

    return mapping.findForward(forward);
  }

  @Override
  protected String getPageTitleKey() {
    return "sample.batchentry.setup.title";
  }

  @Override
  protected String getPageSubtitleKey() {
    return "sample.batchentry.setup.title";
  }
}
