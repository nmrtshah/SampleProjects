/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.apps.finstudio.finmaster;

/**
 *
 * @author Sonam Patel
 */
public class FinMasterFormBean
{

    private String cmbProjectName;
    private String txtModuleName;
    private String txtOrigModulName;
    private String txtReqNo;
    private String txtProblemStmt;
    private String txtSolution;
    private String txtExistPractice;
    private String txtPlacement;
    private String cmbAliasName;
    private String txtMasterTable;
    private String cmbMasterTable;
    private String cmbMasterTablePrimKey;
    private String rdoAliasServer;
    private String cmbServerName;
    private String txtSequence;
    private String txtDataBaseType;
    private boolean chkAdd;
    private boolean chkEdit;
    private boolean chkDelete;
    private boolean chkView;
    private boolean chkHeader;
    private boolean chkFooter;
    private String txtParamName;
    private String empCode;
    //Add Tab
    private String hdnAddField[];
    private String hdnAddControl[];
    private String cmbAddControlPos;
    private boolean hdnChkShowAdd[];
    private boolean hdnChkShowEdit[];
    private boolean hdnChkShowDel[];
    private String chkViewColumn[];
    //Code Properties of Add Tab
    private String hdntxtAddId[];
    private String hdntxtAddName[];
    private String hdntxtAddStyle[];
    private String hdntxtAddSize[];
    private String hdntxtAddClass[];
    private String hdncmbAddOnchange[];
    private String hdnrbtnAddMultiple[];
    private String hdnrbtnAddChecked[];
    private String hdntxtAddMaxLength[];
    private String hdnrbtnAddReadonly[];
    private String hdncmbAddAlign[];
    private String hdntxtAddRows[];
    private String hdntxtAddCols[];
    private String hdntxtAddValue[];
    private String hdnAddTabIndex[];
    private String hdnrbtnAddValueFormat[];
    private String hdntxtAddTotalRdo[];
    private String hdntxtAddRdoCap[];
    private String hdntxtAddRdoVal[];
    private String hdntxtAddRdoDefVal[];
    private String hdntxtAddLabel[];
    private String hdnchkAddSrc[];
    private String hdnrdoAddDataSrc[];
    private String hdntxtAddSrcStatic[];
    private String hdntxtAddSrcQuery[];
    private String txtAddSrcQuery;
    private String hdntxtAddWsdlUrl[];
    private String hdncmbAddWsMethod[];
    private String hdntxtAddWsCmbValue[];
    private String hdntxtAddWsCmbText[];
    private String hdntxtAddWsProject[];
    private String hdntxtAddWsIntrface[];
    private String hdntxtAddWsRetType[];
    private String hdntxtAddWsParams[];
    private String hdntxtAddWsExps[];
    private String txtAddWsdlUrl;
    private String cmbAddWsMethod;
    private String txtAddWsProject;
    private String txtAddWsIntrface;
    private String hdnchkAddMandatory[];
    private String hdncmbAddCommonQuery[];
    private String hdntxtAddMaxsize[];
    private String hdntxtAddType[];
    private String hdntxtAddMaxfiles[];
    private String hdntxtAddEleName[];
    private String hdntxtAddDispTxt[];
    private String hdntxtAddOnremoveCall[];
    //SRS Properties of Add Tab
    private String hdncmbAddValidation[];
    private String hdntxtAddRemarks[];
    //Edit Tab
    private String hdnEditField[];
    private String hdnEditControl[];
    private String cmbEditControlPos;
    //Code Properties of Edit Tab
    private String hdntxtEditId[];
    private String hdntxtEditName[];
    private String hdntxtEditStyle[];
    private String hdntxtEditSize[];
    private String hdntxtEditClass[];
    private String hdncmbEditOnchange[];
    private String hdnrbtnEditMultiple[];
    private String hdnrbtnEditChecked[];
    private String hdntxtEditMaxLength[];
    private String hdnrbtnEditReadonly[];
    private String hdncmbEditAlign[];
    private String hdntxtEditRows[];
    private String hdntxtEditCols[];
    private String hdntxtEditValue[];
    private String hdnEditTabIndex[];
    private String hdnrbtnEditValueFormat[];
    private String hdntxtEditTotalRdo[];
    private String hdntxtEditRdoCap[];
    private String hdntxtEditRdoVal[];
    private String hdntxtEditRdoDefVal[];
    private String hdntxtEditLabel[];
    private String hdnchkEditSrc[];
    private String hdnrdoEditDataSrc[];
    private String hdntxtEditSrcStatic[];
    private String hdntxtEditSrcQuery[];
    private String txtEditSrcQuery;
    private String hdntxtEditWsdlUrl[];
    private String hdncmbEditWsMethod[];
    private String hdntxtEditWsCmbValue[];
    private String hdntxtEditWsCmbText[];
    private String hdntxtEditWsProject[];
    private String hdntxtEditWsIntrface[];
    private String hdntxtEditWsRetType[];
    private String hdntxtEditWsParams[];
    private String hdntxtEditWsExps[];
    private String txtEditWsdlUrl;
    private String cmbEditWsMethod;
    private String txtEditWsProject;
    private String txtEditWsIntrface;
    private String hdnchkEditMandatory[];
    private String hdncmbEditCommonQuery[];
    private String hdntxtEditMaxsize[];
    private String hdntxtEditType[];
    private String hdntxtEditMaxfiles[];
    private String hdntxtEditEleName[];
    private String hdntxtEditDispTxt[];
    private String hdntxtEditOnremoveCall[];
    //SRS Properties of Edit Tab
    private String hdncmbEditValidation[];
    private String hdntxtEditRemarks[];
    //Delete Tab
    private String hdnDeleteField[];
    private String hdnDeleteControl[];
    private String cmbDeleteControlPos;
    //Code Properties of Delete Tab
    private String hdntxtDeleteId[];
    private String hdntxtDeleteName[];
    private String hdntxtDeleteStyle[];
    private String hdntxtDeleteSize[];
    private String hdntxtDeleteClass[];
    private String hdncmbDeleteOnchange[];
    private String hdnrbtnDeleteMultiple[];
    private String hdnrbtnDeleteChecked[];
    private String hdntxtDeleteMaxLength[];
    private String hdnrbtnDeleteReadonly[];
    private String hdncmbDeleteAlign[];
    private String hdntxtDeleteRows[];
    private String hdntxtDeleteCols[];
    private String hdntxtDeleteValue[];
    private String hdnDeleteTabIndex[];
    private String hdnrbtnDeleteValueFormat[];
    private String hdntxtDeleteTotalRdo[];
    private String hdntxtDeleteRdoCap[];
    private String hdntxtDeleteRdoVal[];
    private String hdntxtDeleteRdoDefVal[];
    private String hdntxtDeleteLabel[];
    private String hdnchkDeleteSrc[];
    private String hdnrdoDeleteDataSrc[];
    private String hdntxtDeleteSrcStatic[];
    private String hdntxtDeleteSrcQuery[];
    private String txtDeleteSrcQuery;
    private String hdntxtDeleteWsdlUrl[];
    private String hdncmbDeleteWsMethod[];
    private String hdntxtDeleteWsCmbValue[];
    private String hdntxtDeleteWsCmbText[];
    private String hdntxtDeleteWsProject[];
    private String hdntxtDeleteWsIntrface[];
    private String hdntxtDeleteWsRetType[];
    private String hdntxtDeleteWsParams[];
    private String hdntxtDeleteWsExps[];
    private String txtDeleteWsdlUrl;
    private String cmbDeleteWsMethod;
    private String txtDeleteWsProject;
    private String txtDeleteWsIntrface;
    private String hdnchkDeleteMandatory[];
    private String hdncmbDeleteCommonQuery[];
    private String hdntxtDeleteMaxsize[];
    private String hdntxtDeleteType[];
    private String hdntxtDeleteMaxfiles[];
    private String hdntxtDeleteEleName[];
    private String hdntxtDeleteDispTxt[];
    private String hdntxtDeleteOnremoveCall[];
    //SRS Properties of Delete Tab
    private String hdncmbDeleteValidation[];
    private String hdntxtDeleteRemarks[];
    //View Tab
    private boolean chkColumns;
    private boolean chkOnScreen;
    private boolean chkPdf;
    private boolean chkXls;
    private String hdnViewField[];
    private String hdnViewControl[];
    private String cmbViewControlPos;
    //Code Properties of View Tab
    private String hdntxtViewId[];
    private String hdntxtViewName[];
    private String hdntxtViewStyle[];
    private String hdntxtViewSize[];
    private String hdntxtViewClass[];
    private String hdncmbViewOnchange[];
    private String hdnrbtnViewMultiple[];
    private String hdnrbtnViewChecked[];
    private String hdntxtViewMaxLength[];
    private String hdnrbtnViewReadonly[];
    private String hdncmbViewAlign[];
    private String hdntxtViewRows[];
    private String hdntxtViewCols[];
    private String hdntxtViewValue[];
    private String hdnViewTabIndex[];
    private String hdnrbtnViewValueFormat[];
    private String hdntxtViewTotalRdo[];
    private String hdntxtViewRdoCap[];
    private String hdntxtViewRdoVal[];
    private String hdntxtViewRdoDefVal[];
    private String hdntxtViewLabel[];
    private String hdnchkViewSrc[];
    private String hdnrdoViewDataSrc[];
    private String hdntxtViewSrcStatic[];
    private String hdntxtViewSrcQuery[];
    private String txtViewSrcQuery;
    private String hdntxtViewWsdlUrl[];
    private String hdncmbViewWsMethod[];
    private String hdntxtViewWsCmbValue[];
    private String hdntxtViewWsCmbText[];
    private String hdntxtViewWsProject[];
    private String hdntxtViewWsIntrface[];
    private String hdntxtViewWsRetType[];
    private String hdntxtViewWsParams[];
    private String hdntxtViewWsExps[];
    private String txtViewWsdlUrl;
    private String cmbViewWsMethod;
    private String txtViewWsProject;
    private String txtViewWsIntrface;
    private String hdnchkViewMandatory[];
    private String hdncmbViewCommonQuery[];
    private String hdntxtViewMaxsize[];
    private String hdntxtViewType[];
    private String hdntxtViewMaxfiles[];
    private String hdntxtViewEleName[];
    private String hdntxtViewDispTxt[];
    private String hdntxtViewOnremoveCall[];
    //SRS Properties of View Tab
    private String hdncmbViewValidation[];
    private String hdntxtViewRemarks[];
    private int SrNo;
    private boolean schemaisalias;
    private String hdnMstAllColumns[];
    private String hdnMstAllDataTypes[];

    public String getCmbProjectName()
    {
        return cmbProjectName;
    }

    public void setCmbProjectName(final String cmbProjectName)
    {
        this.cmbProjectName = cmbProjectName;
    }

    public String getTxtModuleName()
    {
        return txtModuleName;
    }

    public void setTxtModuleName(final String txtModuleName)
    {
        this.txtModuleName = txtModuleName;
    }

    public String getTxtOrigModulName()
    {
        return txtOrigModulName;
    }

    public void setTxtOrigModulName(final String txtOrigModulName)
    {
        this.txtOrigModulName = txtOrigModulName;
    }

    public String getTxtReqNo()
    {
        return txtReqNo;
    }

    public void setTxtReqNo(final String txtReqNo)
    {
        this.txtReqNo = txtReqNo;
    }

    public String getTxtProblemStmt()
    {
        return txtProblemStmt;
    }

    public void setTxtProblemStmt(final String txtProblemStmt)
    {
        this.txtProblemStmt = txtProblemStmt;
    }

    public String getTxtSolution()
    {
        return txtSolution;
    }

    public void setTxtSolution(final String txtSolution)
    {
        this.txtSolution = txtSolution;
    }

    public String getTxtExistPractice()
    {
        return txtExistPractice;
    }

    public void setTxtExistPractice(final String txtExistPractice)
    {
        this.txtExistPractice = txtExistPractice;
    }

    public String getTxtPlacement()
    {
        return txtPlacement;
    }

    public void setTxtPlacement(final String txtPlacement)
    {
        this.txtPlacement = txtPlacement;
    }

    public String getCmbAliasName()
    {
        return cmbAliasName;
    }

    public void setCmbAliasName(final String cmbAliasName)
    {
        this.cmbAliasName = cmbAliasName;
    }

    public String getCmbMasterTable()
    {
        return cmbMasterTable;
    }

    public void setCmbMasterTable(final String cmbMasterTable)
    {
        this.cmbMasterTable = cmbMasterTable;
    }

    public String getTxtMasterTable()
    {
        return txtMasterTable;
    }

    public void setTxtMasterTable(final String txtMasterTable)
    {
        this.txtMasterTable = txtMasterTable;
    }

    public String getCmbMasterTablePrimKey()
    {
        return cmbMasterTablePrimKey;
    }

    public void setCmbMasterTablePrimKey(final String cmbMasterTablePrimKey)
    {
        this.cmbMasterTablePrimKey = cmbMasterTablePrimKey;
    }

    public String getRdoAliasServer()
    {
        return rdoAliasServer;
    }

    public void setRdoAliasServer(final String rdoAliasServer)
    {
        this.rdoAliasServer = rdoAliasServer;
    }

    public String getCmbServerName()
    {
        return cmbServerName;
    }

    public void setCmbServerName(final String cmbServerName)
    {
        this.cmbServerName = cmbServerName;
    }

    public String getTxtSequence()
    {
        return txtSequence;
    }

    public void setTxtSequence(final String txtSequence)
    {
        this.txtSequence = txtSequence;
    }

    public String getTxtDataBaseType()
    {
        return txtDataBaseType;
    }

    public void setTxtDataBaseType(final String txtDataBaseType)
    {
        this.txtDataBaseType = txtDataBaseType;
    }

    public boolean isChkAdd()
    {
        return chkAdd;
    }

    public void setChkAdd(final boolean chkAdd)
    {
        this.chkAdd = chkAdd;
    }

    public boolean isChkEdit()
    {
        return chkEdit;
    }

    public void setChkEdit(final boolean chkEdit)
    {
        this.chkEdit = chkEdit;
    }

    public boolean isChkDelete()
    {
        return chkDelete;
    }

    public void setChkDelete(final boolean chkDelete)
    {
        this.chkDelete = chkDelete;
    }

    public boolean isChkView()
    {
        return chkView;
    }

    public void setChkView(final boolean chkView)
    {
        this.chkView = chkView;
    }

    public boolean isChkHeader()
    {
        return chkHeader;
    }

    public void setChkHeader(final boolean chkHeader)
    {
        this.chkHeader = chkHeader;
    }

    public boolean isChkFooter()
    {
        return chkFooter;
    }

    public void setChkFooter(final boolean chkFooter)
    {
        this.chkFooter = chkFooter;
    }

    public String getTxtParamName()
    {
        return txtParamName;
    }

    public void setTxtParamName(final String txtParamName)
    {
        this.txtParamName = txtParamName;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public void setEmpCode(final String empCode)
    {
        this.empCode = empCode;
    }

    //Add Tab's Variables starts
    public String[] getHdnAddField()
    {
        return hdnAddField;
    }

    public void setHdnAddField(final String[] hdnAddField)
    {
        this.hdnAddField = hdnAddField;
    }

    public String[] getHdnAddControl()
    {
        return hdnAddControl;
    }

    public void setHdnAddControl(final String[] hdnAddControl)
    {
        this.hdnAddControl = hdnAddControl;
    }

    public String getCmbAddControlPos()
    {
        return cmbAddControlPos;
    }

    public void setCmbAddControlPos(final String cmbAddControlPos)
    {
        this.cmbAddControlPos = cmbAddControlPos;
    }

    public String[] getHdnAddTabIndex()
    {
        return hdnAddTabIndex;
    }

    public void setHdnAddTabIndex(final String[] hdnAddTabIndex)
    {
        this.hdnAddTabIndex = hdnAddTabIndex;
    }

    public boolean[] getHdnChkShowAdd()
    {
        return hdnChkShowAdd;
    }

    public void setHdnChkShowAdd(final boolean[] hdnChkShowAdd)
    {
        this.hdnChkShowAdd = hdnChkShowAdd;
    }

    public boolean[] getHdnChkShowEdit()
    {
        return hdnChkShowEdit;
    }

    public void setHdnChkShowEdit(final boolean[] hdnChkShowEdit)
    {
        this.hdnChkShowEdit = hdnChkShowEdit;
    }

    public boolean[] getHdnChkShowDel()
    {
        return hdnChkShowDel;
    }

    public void setHdnChkShowDel(final boolean[] hdnChkShowDel)
    {
        this.hdnChkShowDel = hdnChkShowDel;
    }

    public String[] getChkViewColumn()
    {
        return chkViewColumn;
    }

    public void setChkViewColumn(final String[] chkViewColumn)
    {
        this.chkViewColumn = chkViewColumn;
    }

    public String[] getHdncmbAddAlign()
    {
        return hdncmbAddAlign;
    }

    public void setHdncmbAddAlign(final String[] hdncmbAddAlign)
    {
        this.hdncmbAddAlign = hdncmbAddAlign;
    }

    public String[] getHdncmbAddValidation()
    {
        return hdncmbAddValidation;
    }

    public void setHdncmbAddValidation(final String[] hdncmbAddValidation)
    {
        this.hdncmbAddValidation = hdncmbAddValidation;
    }

    public String[] getHdnrbtnAddChecked()
    {
        return hdnrbtnAddChecked;
    }

    public void setHdnrbtnAddChecked(final String[] hdnrbtnAddChecked)
    {
        this.hdnrbtnAddChecked = hdnrbtnAddChecked;
    }

    public String[] getHdnrbtnAddMultiple()
    {
        return hdnrbtnAddMultiple;
    }

    public void setHdnrbtnAddMultiple(final String[] hdnrbtnAddMultiple)
    {
        this.hdnrbtnAddMultiple = hdnrbtnAddMultiple;
    }

    public String[] getHdnrbtnAddReadonly()
    {
        return hdnrbtnAddReadonly;
    }

    public void setHdnrbtnAddReadonly(final String[] hdnrbtnAddReadonly)
    {
        this.hdnrbtnAddReadonly = hdnrbtnAddReadonly;
    }

    public String[] getHdntxtAddClass()
    {
        return hdntxtAddClass;
    }

    public void setHdntxtAddClass(final String[] hdntxtAddClass)
    {
        this.hdntxtAddClass = hdntxtAddClass;
    }

    public String[] getHdncmbAddOnchange()
    {
        return hdncmbAddOnchange;
    }

    public void setHdncmbAddOnchange(final String[] hdncmbAddOnchange)
    {
        this.hdncmbAddOnchange = hdncmbAddOnchange;
    }

    public String[] getHdntxtAddCols()
    {
        return hdntxtAddCols;
    }

    public void setHdntxtAddCols(final String[] hdntxtAddCols)
    {
        this.hdntxtAddCols = hdntxtAddCols;
    }

    public String[] getHdntxtAddId()
    {
        return hdntxtAddId;
    }

    public void setHdntxtAddId(final String[] hdntxtAddId)
    {
        this.hdntxtAddId = hdntxtAddId;
    }

    public String[] getHdntxtAddMaxLength()
    {
        return hdntxtAddMaxLength;
    }

    public void setHdntxtAddMaxLength(final String[] hdntxtAddMaxLength)
    {
        this.hdntxtAddMaxLength = hdntxtAddMaxLength;
    }

    public String[] getHdntxtAddName()
    {
        return hdntxtAddName;
    }

    public void setHdntxtAddName(final String[] hdntxtAddName)
    {
        this.hdntxtAddName = hdntxtAddName;
    }

    public String[] getHdntxtAddRemarks()
    {
        return hdntxtAddRemarks;
    }

    public void setHdntxtAddRemarks(final String[] hdntxtAddRemarks)
    {
        this.hdntxtAddRemarks = hdntxtAddRemarks;
    }

    public String[] getHdntxtAddRows()
    {
        return hdntxtAddRows;
    }

    public void setHdntxtAddRows(final String[] hdntxtAddRows)
    {
        this.hdntxtAddRows = hdntxtAddRows;
    }

    public String[] getHdntxtAddSize()
    {
        return hdntxtAddSize;
    }

    public void setHdntxtAddSize(final String[] hdntxtAddSize)
    {
        this.hdntxtAddSize = hdntxtAddSize;
    }

    public String[] getHdntxtAddStyle()
    {
        return hdntxtAddStyle;
    }

    public void setHdntxtAddStyle(final String[] hdntxtAddStyle)
    {
        this.hdntxtAddStyle = hdntxtAddStyle;
    }

    public String[] getHdntxtAddValue()
    {
        return hdntxtAddValue;
    }

    public void setHdntxtAddValue(final String[] hdntxtAddValue)
    {
        this.hdntxtAddValue = hdntxtAddValue;
    }

    public String[] getHdnrbtnAddValueFormat()
    {
        return hdnrbtnAddValueFormat;
    }

    public void setHdnrbtnAddValueFormat(final String[] hdnrbtnAddValueFormat)
    {
        this.hdnrbtnAddValueFormat = hdnrbtnAddValueFormat;
    }

    public String[] getHdntxtAddRdoCap()
    {
        return hdntxtAddRdoCap;
    }

    public void setHdntxtAddRdoCap(final String[] hdntxtAddRdoCap)
    {
        this.hdntxtAddRdoCap = hdntxtAddRdoCap;
    }

    public String[] getHdntxtAddRdoVal()
    {
        return hdntxtAddRdoVal;
    }

    public void setHdntxtAddRdoVal(final String[] hdntxtAddRdoVal)
    {
        this.hdntxtAddRdoVal = hdntxtAddRdoVal;
    }

    public String[] getHdntxtAddRdoDefVal()
    {
        return hdntxtAddRdoDefVal;
    }

    public void setHdntxtAddRdoDefVal(final String[] hdntxtAddRdoDefVal)
    {
        this.hdntxtAddRdoDefVal = hdntxtAddRdoDefVal;
    }

    public String[] getHdntxtAddTotalRdo()
    {
        return hdntxtAddTotalRdo;
    }

    public void setHdntxtAddTotalRdo(final String[] hdntxtAddTotalRdo)
    {
        this.hdntxtAddTotalRdo = hdntxtAddTotalRdo;
    }

    public String[] getHdntxtAddLabel()
    {
        return hdntxtAddLabel;
    }

    public void setHdntxtAddLabel(final String[] hdntxtAddLabel)
    {
        this.hdntxtAddLabel = hdntxtAddLabel;
    }

    public String[] getHdnchkAddSrc()
    {
        return hdnchkAddSrc;
    }

    public void setHdnchkAddSrc(final String[] hdnchkAddSrc)
    {
        this.hdnchkAddSrc = hdnchkAddSrc;
    }

    public String[] getHdnrdoAddDataSrc()
    {
        return hdnrdoAddDataSrc;
    }

    public void setHdnrdoAddDataSrc(final String[] hdnrdoAddDataSrc)
    {
        this.hdnrdoAddDataSrc = hdnrdoAddDataSrc;
    }

    public String[] getHdntxtAddSrcStatic()
    {
        return hdntxtAddSrcStatic;
    }

    public void setHdntxtAddSrcStatic(final String[] hdntxtAddSrcStatic)
    {
        this.hdntxtAddSrcStatic = hdntxtAddSrcStatic;
    }

    public String[] getHdntxtAddSrcQuery()
    {
        return hdntxtAddSrcQuery;
    }

    public void setHdntxtAddSrcQuery(final String[] hdntxtAddSrcQuery)
    {
        this.hdntxtAddSrcQuery = hdntxtAddSrcQuery;
    }

    public String getTxtAddSrcQuery()
    {
        return txtAddSrcQuery;
    }

    public void setTxtAddSrcQuery(final String txtAddSrcQuery)
    {
        this.txtAddSrcQuery = txtAddSrcQuery;
    }

    public String[] getHdntxtAddWsdlUrl()
    {
        return hdntxtAddWsdlUrl;
    }

    public void setHdntxtAddWsdlUrl(final String[] hdntxtAddWsdlUrl)
    {
        this.hdntxtAddWsdlUrl = hdntxtAddWsdlUrl;
    }

    public String[] getHdncmbAddWsMethod()
    {
        return hdncmbAddWsMethod;
    }

    public void setHdncmbAddWsMethod(final String[] hdncmbAddWsMethod)
    {
        this.hdncmbAddWsMethod = hdncmbAddWsMethod;
    }

    public String[] getHdntxtAddWsCmbValue()
    {
        return hdntxtAddWsCmbValue;
    }

    public void setHdntxtAddWsCmbValue(final String[] hdntxtAddWsCmbValue)
    {
        this.hdntxtAddWsCmbValue = hdntxtAddWsCmbValue;
    }

    public String[] getHdntxtAddWsCmbText()
    {
        return hdntxtAddWsCmbText;
    }

    public void setHdntxtAddWsCmbText(final String[] hdntxtAddWsCmbText)
    {
        this.hdntxtAddWsCmbText = hdntxtAddWsCmbText;
    }

    public String[] getHdntxtAddWsProject()
    {
        return hdntxtAddWsProject;
    }

    public void setHdntxtAddWsProject(final String[] hdntxtAddWsProject)
    {
        this.hdntxtAddWsProject = hdntxtAddWsProject;
    }

    public String[] getHdntxtAddWsIntrface()
    {
        return hdntxtAddWsIntrface;
    }

    public void setHdntxtAddWsIntrface(final String[] hdntxtAddWsIntrface)
    {
        this.hdntxtAddWsIntrface = hdntxtAddWsIntrface;
    }

    public String[] getHdntxtAddWsRetType()
    {
        return hdntxtAddWsRetType;
    }

    public void setHdntxtAddWsRetType(final String[] hdntxtAddWsRetType)
    {
        this.hdntxtAddWsRetType = hdntxtAddWsRetType;
    }

    public String[] getHdntxtAddWsParams()
    {
        return hdntxtAddWsParams;
    }

    public void setHdntxtAddWsParams(final String[] hdntxtAddWsParams)
    {
        this.hdntxtAddWsParams = hdntxtAddWsParams;
    }

    public String[] getHdntxtAddWsExps()
    {
        return hdntxtAddWsExps;
    }

    public void setHdntxtAddWsExps(final String[] hdntxtAddWsExps)
    {
        this.hdntxtAddWsExps = hdntxtAddWsExps;
    }

    public String getTxtAddWsdlUrl()
    {
        return txtAddWsdlUrl;
    }

    public void setTxtAddWsdlUrl(final String txtAddWsdlUrl)
    {
        this.txtAddWsdlUrl = txtAddWsdlUrl;
    }

    public String getCmbAddWsMethod()
    {
        return cmbAddWsMethod;
    }

    public void setCmbAddWsMethod(final String cmbAddWsMethod)
    {
        this.cmbAddWsMethod = cmbAddWsMethod;
    }

    public String getTxtAddWsProject()
    {
        return txtAddWsProject;
    }

    public void setTxtAddWsProject(final String txtAddWsProject)
    {
        this.txtAddWsProject = txtAddWsProject;
    }

    public String getTxtAddWsIntrface()
    {
        return txtAddWsIntrface;
    }

    public void setTxtAddWsIntrface(final String txtAddWsIntrface)
    {
        this.txtAddWsIntrface = txtAddWsIntrface;
    }

    public String[] getHdnchkAddMandatory()
    {
        return hdnchkAddMandatory;
    }

    public void setHdnchkAddMandatory(final String[] hdnchkAddMandatory)
    {
        this.hdnchkAddMandatory = hdnchkAddMandatory;
    }

    public String[] getHdncmbAddCommonQuery()
    {
        return hdncmbAddCommonQuery;
    }

    public void setHdncmbAddCommonQuery(final String[] hdncmbAddCommonQuery)
    {
        this.hdncmbAddCommonQuery = hdncmbAddCommonQuery;
    }

    public String[] getHdntxtAddMaxsize()
    {
        return hdntxtAddMaxsize;
    }

    public void setHdntxtAddMaxsize(final String[] hdntxtAddMaxsize)
    {
        this.hdntxtAddMaxsize = hdntxtAddMaxsize;
    }

    public String[] getHdntxtAddType()
    {
        return hdntxtAddType;
    }

    public void setHdntxtAddType(final String[] hdntxtAddType)
    {
        this.hdntxtAddType = hdntxtAddType;
    }

    public String[] getHdntxtAddMaxfiles()
    {
        return hdntxtAddMaxfiles;
    }

    public void setHdntxtAddMaxfiles(final String[] hdntxtAddMaxfiles)
    {
        this.hdntxtAddMaxfiles = hdntxtAddMaxfiles;
    }

    public String[] getHdntxtAddEleName()
    {
        return hdntxtAddEleName;
    }

    public void setHdntxtAddEleName(final String[] hdntxtAddEleName)
    {
        this.hdntxtAddEleName = hdntxtAddEleName;
    }

    public String[] getHdntxtAddDispTxt()
    {
        return hdntxtAddDispTxt;
    }

    public void setHdntxtAddDispTxt(final String[] hdntxtAddDispTxt)
    {
        this.hdntxtAddDispTxt = hdntxtAddDispTxt;
    }

    public String[] getHdntxtAddOnremoveCall()
    {
        return hdntxtAddOnremoveCall;
    }

    public void setHdntxtAddOnremoveCall(final String[] hdntxtAddOnremoveCall)
    {
        this.hdntxtAddOnremoveCall = hdntxtAddOnremoveCall;
    }
    //Add Tab's Variables over

    //Edit Tab's Variables starts
    public String[] getHdnEditField()
    {
        return hdnEditField;
    }

    public void setHdnEditField(final String[] hdnEditField)
    {
        this.hdnEditField = hdnEditField;
    }

    public String[] getHdnEditControl()
    {
        return hdnEditControl;
    }

    public void setHdnEditControl(final String[] hdnEditControl)
    {
        this.hdnEditControl = hdnEditControl;
    }

    public String getCmbEditControlPos()
    {
        return cmbEditControlPos;
    }

    public void setCmbEditControlPos(final String cmbEditControlPos)
    {
        this.cmbEditControlPos = cmbEditControlPos;
    }

    public String[] getHdnEditTabIndex()
    {
        return hdnEditTabIndex;
    }

    public void setHdnEditTabIndex(final String[] hdnEditTabIndex)
    {
        this.hdnEditTabIndex = hdnEditTabIndex;
    }

    public String[] getHdncmbEditAlign()
    {
        return hdncmbEditAlign;
    }

    public void setHdncmbEditAlign(final String[] hdncmbEditAlign)
    {
        this.hdncmbEditAlign = hdncmbEditAlign;
    }

    public String[] getHdncmbEditValidation()
    {
        return hdncmbEditValidation;
    }

    public void setHdncmbEditValidation(final String[] hdncmbEditValidation)
    {
        this.hdncmbEditValidation = hdncmbEditValidation;
    }

    public String[] getHdnrbtnEditChecked()
    {
        return hdnrbtnEditChecked;
    }

    public void setHdnrbtnEditChecked(final String[] hdnrbtnEditChecked)
    {
        this.hdnrbtnEditChecked = hdnrbtnEditChecked;
    }

    public String[] getHdnrbtnEditMultiple()
    {
        return hdnrbtnEditMultiple;
    }

    public void setHdnrbtnEditMultiple(final String[] hdnrbtnEditMultiple)
    {
        this.hdnrbtnEditMultiple = hdnrbtnEditMultiple;
    }

    public String[] getHdnrbtnEditReadonly()
    {
        return hdnrbtnEditReadonly;
    }

    public void setHdnrbtnEditReadonly(final String[] hdnrbtnEditReadonly)
    {
        this.hdnrbtnEditReadonly = hdnrbtnEditReadonly;
    }

    public String[] getHdntxtEditClass()
    {
        return hdntxtEditClass;
    }

    public void setHdntxtEditClass(final String[] hdntxtEditClass)
    {
        this.hdntxtEditClass = hdntxtEditClass;
    }

    public String[] getHdncmbEditOnchange()
    {
        return hdncmbEditOnchange;
    }

    public void setHdncmbEditOnchange(final String[] hdncmbEditOnchange)
    {
        this.hdncmbEditOnchange = hdncmbEditOnchange;
    }

    public String[] getHdntxtEditCols()
    {
        return hdntxtEditCols;
    }

    public void setHdntxtEditCols(final String[] hdntxtEditCols)
    {
        this.hdntxtEditCols = hdntxtEditCols;
    }

    public String[] getHdntxtEditId()
    {
        return hdntxtEditId;
    }

    public void setHdntxtEditId(final String[] hdntxtEditId)
    {
        this.hdntxtEditId = hdntxtEditId;
    }

    public String[] getHdntxtEditMaxLength()
    {
        return hdntxtEditMaxLength;
    }

    public void setHdntxtEditMaxLength(final String[] hdntxtEditMaxLength)
    {
        this.hdntxtEditMaxLength = hdntxtEditMaxLength;
    }

    public String[] getHdntxtEditName()
    {
        return hdntxtEditName;
    }

    public void setHdntxtEditName(final String[] hdntxtEditName)
    {
        this.hdntxtEditName = hdntxtEditName;
    }

    public String[] getHdntxtEditRemarks()
    {
        return hdntxtEditRemarks;
    }

    public void setHdntxtEditRemarks(final String[] hdntxtEditRemarks)
    {
        this.hdntxtEditRemarks = hdntxtEditRemarks;
    }

    public String[] getHdntxtEditRows()
    {
        return hdntxtEditRows;
    }

    public void setHdntxtEditRows(final String[] hdntxtEditRows)
    {
        this.hdntxtEditRows = hdntxtEditRows;
    }

    public String[] getHdntxtEditSize()
    {
        return hdntxtEditSize;
    }

    public void setHdntxtEditSize(final String[] hdntxtEditSize)
    {
        this.hdntxtEditSize = hdntxtEditSize;
    }

    public String[] getHdntxtEditStyle()
    {
        return hdntxtEditStyle;
    }

    public void setHdntxtEditStyle(final String[] hdntxtEditStyle)
    {
        this.hdntxtEditStyle = hdntxtEditStyle;
    }

    public String[] getHdntxtEditValue()
    {
        return hdntxtEditValue;
    }

    public void setHdntxtEditValue(final String[] hdntxtEditValue)
    {
        this.hdntxtEditValue = hdntxtEditValue;
    }

    public String[] getHdnrbtnEditValueFormat()
    {
        return hdnrbtnEditValueFormat;
    }

    public void setHdnrbtnEditValueFormat(final String[] hdnrbtnEditValueFormat)
    {
        this.hdnrbtnEditValueFormat = hdnrbtnEditValueFormat;
    }

    public String[] getHdntxtEditRdoCap()
    {
        return hdntxtEditRdoCap;
    }

    public void setHdntxtEditRdoCap(final String[] hdntxtEditRdoCap)
    {
        this.hdntxtEditRdoCap = hdntxtEditRdoCap;
    }

    public String[] getHdntxtEditRdoVal()
    {
        return hdntxtEditRdoVal;
    }

    public void setHdntxtEditRdoVal(final String[] hdntxtEditRdoVal)
    {
        this.hdntxtEditRdoVal = hdntxtEditRdoVal;
    }

    public String[] getHdntxtEditRdoDefVal()
    {
        return hdntxtEditRdoDefVal;
    }

    public void setHdntxtEditRdoDefVal(final String[] hdntxtEditRdoDefVal)
    {
        this.hdntxtEditRdoDefVal = hdntxtEditRdoDefVal;
    }

    public String[] getHdntxtEditTotalRdo()
    {
        return hdntxtEditTotalRdo;
    }

    public void setHdntxtEditTotalRdo(final String[] hdntxtEditTotalRdo)
    {
        this.hdntxtEditTotalRdo = hdntxtEditTotalRdo;
    }

    public String[] getHdntxtEditLabel()
    {
        return hdntxtEditLabel;
    }

    public void setHdntxtEditLabel(final String[] hdntxtEditLabel)
    {
        this.hdntxtEditLabel = hdntxtEditLabel;
    }

    public String[] getHdnchkEditSrc()
    {
        return hdnchkEditSrc;
    }

    public void setHdnchkEditSrc(final String[] hdnchkEditSrc)
    {
        this.hdnchkEditSrc = hdnchkEditSrc;
    }

    public String[] getHdnrdoEditDataSrc()
    {
        return hdnrdoEditDataSrc;
    }

    public void setHdnrdoEditDataSrc(final String[] hdnrdoEditDataSrc)
    {
        this.hdnrdoEditDataSrc = hdnrdoEditDataSrc;
    }

    public String[] getHdntxtEditSrcStatic()
    {
        return hdntxtEditSrcStatic;
    }

    public void setHdntxtEditSrcStatic(final String[] hdntxtEditSrcStatic)
    {
        this.hdntxtEditSrcStatic = hdntxtEditSrcStatic;
    }

    public String[] getHdntxtEditSrcQuery()
    {
        return hdntxtEditSrcQuery;
    }

    public void setHdntxtEditSrcQuery(final String[] hdntxtEditSrcQuery)
    {
        this.hdntxtEditSrcQuery = hdntxtEditSrcQuery;
    }

    public String getTxtEditSrcQuery()
    {
        return txtEditSrcQuery;
    }

    public void setTxtEditSrcQuery(final String txtEditSrcQuery)
    {
        this.txtEditSrcQuery = txtEditSrcQuery;
    }

    public String[] getHdntxtEditWsdlUrl()
    {
        return hdntxtEditWsdlUrl;
    }

    public void setHdntxtEditWsdlUrl(final String[] hdntxtEditWsdlUrl)
    {
        this.hdntxtEditWsdlUrl = hdntxtEditWsdlUrl;
    }

    public String[] getHdncmbEditWsMethod()
    {
        return hdncmbEditWsMethod;
    }

    public void setHdncmbEditWsMethod(final String[] hdncmbEditWsMethod)
    {
        this.hdncmbEditWsMethod = hdncmbEditWsMethod;
    }

    public String[] getHdntxtEditWsCmbValue()
    {
        return hdntxtEditWsCmbValue;
    }

    public void setHdntxtEditWsCmbValue(final String[] hdntxtEditWsCmbValue)
    {
        this.hdntxtEditWsCmbValue = hdntxtEditWsCmbValue;
    }

    public String[] getHdntxtEditWsCmbText()
    {
        return hdntxtEditWsCmbText;
    }

    public void setHdntxtEditWsCmbText(final String[] hdntxtEditWsCmbText)
    {
        this.hdntxtEditWsCmbText = hdntxtEditWsCmbText;
    }

    public String[] getHdntxtEditWsProject()
    {
        return hdntxtEditWsProject;
    }

    public void setHdntxtEditWsProject(final String[] hdntxtEditWsProject)
    {
        this.hdntxtEditWsProject = hdntxtEditWsProject;
    }

    public String[] getHdntxtEditWsIntrface()
    {
        return hdntxtEditWsIntrface;
    }

    public void setHdntxtEditWsIntrface(final String[] hdntxtEditWsIntrface)
    {
        this.hdntxtEditWsIntrface = hdntxtEditWsIntrface;
    }

    public String[] getHdntxtEditWsRetType()
    {
        return hdntxtEditWsRetType;
    }

    public void setHdntxtEditWsRetType(final String[] hdntxtEditWsRetType)
    {
        this.hdntxtEditWsRetType = hdntxtEditWsRetType;
    }

    public String[] getHdntxtEditWsParams()
    {
        return hdntxtEditWsParams;
    }

    public void setHdntxtEditWsParams(final String[] hdntxtEditWsParams)
    {
        this.hdntxtEditWsParams = hdntxtEditWsParams;
    }

    public String[] getHdntxtEditWsExps()
    {
        return hdntxtEditWsExps;
    }

    public void setHdntxtEditWsExps(final String[] hdntxtEditWsExps)
    {
        this.hdntxtEditWsExps = hdntxtEditWsExps;
    }

    public String getTxtEditWsdlUrl()
    {
        return txtEditWsdlUrl;
    }

    public void setTxtEditWsdlUrl(final String txtEditWsdlUrl)
    {
        this.txtEditWsdlUrl = txtEditWsdlUrl;
    }

    public String getCmbEditWsMethod()
    {
        return cmbEditWsMethod;
    }

    public void setCmbEditWsMethod(final String cmbEditWsMethod)
    {
        this.cmbEditWsMethod = cmbEditWsMethod;
    }

    public String getTxtEditWsProject()
    {
        return txtEditWsProject;
    }

    public void setTxtEditWsProject(final String txtEditWsProject)
    {
        this.txtEditWsProject = txtEditWsProject;
    }

    public String getTxtEditWsIntrface()
    {
        return txtEditWsIntrface;
    }

    public void setTxtEditWsIntrface(final String txtEditWsIntrface)
    {
        this.txtEditWsIntrface = txtEditWsIntrface;
    }

    public String[] getHdnchkEditMandatory()
    {
        return hdnchkEditMandatory;
    }

    public void setHdnchkEditMandatory(final String[] hdnchkEditMandatory)
    {
        this.hdnchkEditMandatory = hdnchkEditMandatory;
    }

    public String[] getHdncmbEditCommonQuery()
    {
        return hdncmbEditCommonQuery;
    }

    public void setHdncmbEditCommonQuery(final String[] hdncmbEditCommonQuery)
    {
        this.hdncmbEditCommonQuery = hdncmbEditCommonQuery;
    }

    public String[] getHdntxtEditMaxsize()
    {
        return hdntxtEditMaxsize;
    }

    public void setHdntxtEditMaxsize(final String[] hdntxtEditMaxsize)
    {
        this.hdntxtEditMaxsize = hdntxtEditMaxsize;
    }

    public String[] getHdntxtEditType()
    {
        return hdntxtEditType;
    }

    public void setHdntxtEditType(final String[] hdntxtEditType)
    {
        this.hdntxtEditType = hdntxtEditType;
    }

    public String[] getHdntxtEditMaxfiles()
    {
        return hdntxtEditMaxfiles;
    }

    public void setHdntxtEditMaxfiles(final String[] hdntxtEditMaxfiles)
    {
        this.hdntxtEditMaxfiles = hdntxtEditMaxfiles;
    }

    public String[] getHdntxtEditEleName()
    {
        return hdntxtEditEleName;
    }

    public void setHdntxtEditEleName(final String[] hdntxtEditEleName)
    {
        this.hdntxtEditEleName = hdntxtEditEleName;
    }

    public String[] getHdntxtEditDispTxt()
    {
        return hdntxtEditDispTxt;
    }

    public void setHdntxtEditDispTxt(final String[] hdntxtEditDispTxt)
    {
        this.hdntxtEditDispTxt = hdntxtEditDispTxt;
    }

    public String[] getHdntxtEditOnremoveCall()
    {
        return hdntxtEditOnremoveCall;
    }

    public void setHdntxtEditOnremoveCall(final String[] hdntxtEditOnremoveCall)
    {
        this.hdntxtEditOnremoveCall = hdntxtEditOnremoveCall;
    }
    //Edit Tab's Variables over

    //Delete Tab's Variables starts
    public String[] getHdnDeleteField()
    {
        return hdnDeleteField;
    }

    public void setHdnDeleteField(final String[] hdnDeleteField)
    {
        this.hdnDeleteField = hdnDeleteField;
    }

    public String[] getHdnDeleteControl()
    {
        return hdnDeleteControl;
    }

    public void setHdnDeleteControl(final String[] hdnDeleteControl)
    {
        this.hdnDeleteControl = hdnDeleteControl;
    }

    public String getCmbDeleteControlPos()
    {
        return cmbDeleteControlPos;
    }

    public void setCmbDeleteControlPos(final String cmbDeleteControlPos)
    {
        this.cmbDeleteControlPos = cmbDeleteControlPos;
    }

    public String[] getHdnDeleteTabIndex()
    {
        return hdnDeleteTabIndex;
    }

    public void setHdnDeleteTabIndex(final String[] hdnDeleteTabIndex)
    {
        this.hdnDeleteTabIndex = hdnDeleteTabIndex;
    }

    public String[] getHdncmbDeleteAlign()
    {
        return hdncmbDeleteAlign;
    }

    public void setHdncmbDeleteAlign(final String[] hdncmbDeleteAlign)
    {
        this.hdncmbDeleteAlign = hdncmbDeleteAlign;
    }

    public String[] getHdncmbDeleteValidation()
    {
        return hdncmbDeleteValidation;
    }

    public void setHdncmbDeleteValidation(final String[] hdncmbDeleteValidation)
    {
        this.hdncmbDeleteValidation = hdncmbDeleteValidation;
    }

    public String[] getHdnrbtnDeleteChecked()
    {
        return hdnrbtnDeleteChecked;
    }

    public void setHdnrbtnDeleteChecked(final String[] hdnrbtnDeleteChecked)
    {
        this.hdnrbtnDeleteChecked = hdnrbtnDeleteChecked;
    }

    public String[] getHdnrbtnDeleteMultiple()
    {
        return hdnrbtnDeleteMultiple;
    }

    public void setHdnrbtnDeleteMultiple(final String[] hdnrbtnDeleteMultiple)
    {
        this.hdnrbtnDeleteMultiple = hdnrbtnDeleteMultiple;
    }

    public String[] getHdnrbtnDeleteReadonly()
    {
        return hdnrbtnDeleteReadonly;
    }

    public void setHdnrbtnDeleteReadonly(final String[] hdnrbtnDeleteReadonly)
    {
        this.hdnrbtnDeleteReadonly = hdnrbtnDeleteReadonly;
    }

    public String[] getHdntxtDeleteClass()
    {
        return hdntxtDeleteClass;
    }

    public void setHdntxtDeleteClass(final String[] hdntxtDeleteClass)
    {
        this.hdntxtDeleteClass = hdntxtDeleteClass;
    }

    public String[] getHdncmbDeleteOnchange()
    {
        return hdncmbDeleteOnchange;
    }

    public void setHdncmbDeleteOnchange(final String[] hdncmbDeleteOnchange)
    {
        this.hdncmbDeleteOnchange = hdncmbDeleteOnchange;
    }

    public String[] getHdntxtDeleteCols()
    {
        return hdntxtDeleteCols;
    }

    public void setHdntxtDeleteCols(final String[] hdntxtDeleteCols)
    {
        this.hdntxtDeleteCols = hdntxtDeleteCols;
    }

    public String[] getHdntxtDeleteId()
    {
        return hdntxtDeleteId;
    }

    public void setHdntxtDeleteId(final String[] hdntxtDeleteId)
    {
        this.hdntxtDeleteId = hdntxtDeleteId;
    }

    public String[] getHdntxtDeleteMaxLength()
    {
        return hdntxtDeleteMaxLength;
    }

    public void setHdntxtDeleteMaxLength(final String[] hdntxtDeleteMaxLength)
    {
        this.hdntxtDeleteMaxLength = hdntxtDeleteMaxLength;
    }

    public String[] getHdntxtDeleteName()
    {
        return hdntxtDeleteName;
    }

    public void setHdntxtDeleteName(final String[] hdntxtDeleteName)
    {
        this.hdntxtDeleteName = hdntxtDeleteName;
    }

    public String[] getHdntxtDeleteRemarks()
    {
        return hdntxtDeleteRemarks;
    }

    public void setHdntxtDeleteRemarks(final String[] hdntxtDeleteRemarks)
    {
        this.hdntxtDeleteRemarks = hdntxtDeleteRemarks;
    }

    public String[] getHdntxtDeleteRows()
    {
        return hdntxtDeleteRows;
    }

    public void setHdntxtDeleteRows(final String[] hdntxtDeleteRows)
    {
        this.hdntxtDeleteRows = hdntxtDeleteRows;
    }

    public String[] getHdntxtDeleteSize()
    {
        return hdntxtDeleteSize;
    }

    public void setHdntxtDeleteSize(final String[] hdntxtDeleteSize)
    {
        this.hdntxtDeleteSize = hdntxtDeleteSize;
    }

    public String[] getHdntxtDeleteStyle()
    {
        return hdntxtDeleteStyle;
    }

    public void setHdntxtDeleteStyle(final String[] hdntxtDeleteStyle)
    {
        this.hdntxtDeleteStyle = hdntxtDeleteStyle;
    }

    public String[] getHdntxtDeleteValue()
    {
        return hdntxtDeleteValue;
    }

    public void setHdntxtDeleteValue(final String[] hdntxtDeleteValue)
    {
        this.hdntxtDeleteValue = hdntxtDeleteValue;
    }

    public String[] getHdnrbtnDeleteValueFormat()
    {
        return hdnrbtnDeleteValueFormat;
    }

    public void setHdnrbtnDeleteValueFormat(final String[] hdnrbtnDeleteValueFormat)
    {
        this.hdnrbtnDeleteValueFormat = hdnrbtnDeleteValueFormat;
    }

    public String[] getHdntxtDeleteRdoCap()
    {
        return hdntxtDeleteRdoCap;
    }

    public void setHdntxtDeleteRdoCap(final String[] hdntxtDeleteRdoCap)
    {
        this.hdntxtDeleteRdoCap = hdntxtDeleteRdoCap;
    }

    public String[] getHdntxtDeleteRdoVal()
    {
        return hdntxtDeleteRdoVal;
    }

    public void setHdntxtDeleteRdoVal(final String[] hdntxtDeleteRdoVal)
    {
        this.hdntxtDeleteRdoVal = hdntxtDeleteRdoVal;
    }

    public String[] getHdntxtDeleteRdoDefVal()
    {
        return hdntxtDeleteRdoDefVal;
    }

    public void setHdntxtDeleteRdoDefVal(final String[] hdntxtDeleteRdoDefVal)
    {
        this.hdntxtDeleteRdoDefVal = hdntxtDeleteRdoDefVal;
    }

    public String[] getHdntxtDeleteTotalRdo()
    {
        return hdntxtDeleteTotalRdo;
    }

    public void setHdntxtDeleteTotalRdo(final String[] hdntxtDeleteTotalRdo)
    {
        this.hdntxtDeleteTotalRdo = hdntxtDeleteTotalRdo;
    }

    public String[] getHdntxtDeleteLabel()
    {
        return hdntxtDeleteLabel;
    }

    public void setHdntxtDeleteLabel(final String[] hdntxtDeleteLabel)
    {
        this.hdntxtDeleteLabel = hdntxtDeleteLabel;
    }

    public String[] getHdnchkDeleteSrc()
    {
        return hdnchkDeleteSrc;
    }

    public void setHdnchkDeleteSrc(final String[] hdnchkDeleteSrc)
    {
        this.hdnchkDeleteSrc = hdnchkDeleteSrc;
    }

    public String[] getHdnrdoDeleteDataSrc()
    {
        return hdnrdoDeleteDataSrc;
    }

    public void setHdnrdoDeleteDataSrc(final String[] hdnrdoDeleteDataSrc)
    {
        this.hdnrdoDeleteDataSrc = hdnrdoDeleteDataSrc;
    }

    public String[] getHdntxtDeleteSrcStatic()
    {
        return hdntxtDeleteSrcStatic;
    }

    public void setHdntxtDeleteSrcStatic(final String[] hdntxtDeleteSrcStatic)
    {
        this.hdntxtDeleteSrcStatic = hdntxtDeleteSrcStatic;
    }

    public String[] getHdntxtDeleteSrcQuery()
    {
        return hdntxtDeleteSrcQuery;
    }

    public void setHdntxtDeleteSrcQuery(final String[] hdntxtDeleteSrcQuery)
    {
        this.hdntxtDeleteSrcQuery = hdntxtDeleteSrcQuery;
    }

    public String getTxtDeleteSrcQuery()
    {
        return txtDeleteSrcQuery;
    }

    public void setTxtDeleteSrcQuery(final String txtDeleteSrcQuery)
    {
        this.txtDeleteSrcQuery = txtDeleteSrcQuery;
    }

    public String[] getHdntxtDeleteWsdlUrl()
    {
        return hdntxtDeleteWsdlUrl;
    }

    public void setHdntxtDeleteWsdlUrl(final String[] hdntxtDeleteWsdlUrl)
    {
        this.hdntxtDeleteWsdlUrl = hdntxtDeleteWsdlUrl;
    }

    public String[] getHdncmbDeleteWsMethod()
    {
        return hdncmbDeleteWsMethod;
    }

    public void setHdncmbDeleteWsMethod(final String[] hdncmbDeleteWsMethod)
    {
        this.hdncmbDeleteWsMethod = hdncmbDeleteWsMethod;
    }

    public String[] getHdntxtDeleteWsCmbValue()
    {
        return hdntxtDeleteWsCmbValue;
    }

    public void setHdntxtDeleteWsCmbValue(final String[] hdntxtDeleteWsCmbValue)
    {
        this.hdntxtDeleteWsCmbValue = hdntxtDeleteWsCmbValue;
    }

    public String[] getHdntxtDeleteWsCmbText()
    {
        return hdntxtDeleteWsCmbText;
    }

    public void setHdntxtDeleteWsCmbText(final String[] hdntxtDeleteWsCmbText)
    {
        this.hdntxtDeleteWsCmbText = hdntxtDeleteWsCmbText;
    }

    public String[] getHdntxtDeleteWsProject()
    {
        return hdntxtDeleteWsProject;
    }

    public void setHdntxtDeleteWsProject(final String[] hdntxtDeleteWsProject)
    {
        this.hdntxtDeleteWsProject = hdntxtDeleteWsProject;
    }

    public String[] getHdntxtDeleteWsIntrface()
    {
        return hdntxtDeleteWsIntrface;
    }

    public void setHdntxtDeleteWsIntrface(final String[] hdntxtDeleteWsIntrface)
    {
        this.hdntxtDeleteWsIntrface = hdntxtDeleteWsIntrface;
    }

    public String[] getHdntxtDeleteWsRetType()
    {
        return hdntxtDeleteWsRetType;
    }

    public void setHdntxtDeleteWsRetType(final String[] hdntxtDeleteWsRetType)
    {
        this.hdntxtDeleteWsRetType = hdntxtDeleteWsRetType;
    }

    public String[] getHdntxtDeleteWsParams()
    {
        return hdntxtDeleteWsParams;
    }

    public void setHdntxtDeleteWsParams(final String[] hdntxtDeleteWsParams)
    {
        this.hdntxtDeleteWsParams = hdntxtDeleteWsParams;
    }

    public String[] getHdntxtDeleteWsExps()
    {
        return hdntxtDeleteWsExps;
    }

    public void setHdntxtDeleteWsExps(final String[] hdntxtDeleteWsExps)
    {
        this.hdntxtDeleteWsExps = hdntxtDeleteWsExps;
    }

    public String getTxtDeleteWsdlUrl()
    {
        return txtDeleteWsdlUrl;
    }

    public void setTxtDeleteWsdlUrl(final String txtDeleteWsdlUrl)
    {
        this.txtDeleteWsdlUrl = txtDeleteWsdlUrl;
    }

    public String getCmbDeleteWsMethod()
    {
        return cmbDeleteWsMethod;
    }

    public void setCmbDeleteWsMethod(final String cmbDeleteWsMethod)
    {
        this.cmbDeleteWsMethod = cmbDeleteWsMethod;
    }

    public String getTxtDeleteWsProject()
    {
        return txtDeleteWsProject;
    }

    public void setTxtDeleteWsProject(final String txtDeleteWsProject)
    {
        this.txtDeleteWsProject = txtDeleteWsProject;
    }

    public String getTxtDeleteWsIntrface()
    {
        return txtDeleteWsIntrface;
    }

    public void setTxtDeleteWsIntrface(final String txtDeleteWsIntrface)
    {
        this.txtDeleteWsIntrface = txtDeleteWsIntrface;
    }

    public String[] getHdnchkDeleteMandatory()
    {
        return hdnchkDeleteMandatory;
    }

    public void setHdnchkDeleteMandatory(final String[] hdnchkDeleteMandatory)
    {
        this.hdnchkDeleteMandatory = hdnchkDeleteMandatory;
    }

    public String[] getHdncmbDeleteCommonQuery()
    {
        return hdncmbDeleteCommonQuery;
    }

    public void setHdncmbDeleteCommonQuery(final String[] hdncmbDeleteCommonQuery)
    {
        this.hdncmbDeleteCommonQuery = hdncmbDeleteCommonQuery;
    }

    public String[] getHdntxtDeleteMaxsize()
    {
        return hdntxtDeleteMaxsize;
    }

    public void setHdntxtDeleteMaxsize(final String[] hdntxtDeleteMaxsize)
    {
        this.hdntxtDeleteMaxsize = hdntxtDeleteMaxsize;
    }

    public String[] getHdntxtDeleteType()
    {
        return hdntxtDeleteType;
    }

    public void setHdntxtDeleteType(final String[] hdntxtDeleteType)
    {
        this.hdntxtDeleteType = hdntxtDeleteType;
    }

    public String[] getHdntxtDeleteMaxfiles()
    {
        return hdntxtDeleteMaxfiles;
    }

    public void setHdntxtDeleteMaxfiles(final String[] hdntxtDeleteMaxfiles)
    {
        this.hdntxtDeleteMaxfiles = hdntxtDeleteMaxfiles;
    }

    public String[] getHdntxtDeleteEleName()
    {
        return hdntxtDeleteEleName;
    }

    public void setHdntxtDeleteEleName(final String[] hdntxtDeleteEleName)
    {
        this.hdntxtDeleteEleName = hdntxtDeleteEleName;
    }

    public String[] getHdntxtDeleteDispTxt()
    {
        return hdntxtDeleteDispTxt;
    }

    public void setHdntxtDeleteDispTxt(final String[] hdntxtDeleteDispTxt)
    {
        this.hdntxtDeleteDispTxt = hdntxtDeleteDispTxt;
    }

    public String[] getHdntxtDeleteOnremoveCall()
    {
        return hdntxtDeleteOnremoveCall;
    }

    public void setHdntxtDeleteOnremoveCall(final String[] hdntxtDeleteOnremoveCall)
    {
        this.hdntxtDeleteOnremoveCall = hdntxtDeleteOnremoveCall;
    }
    //Delete Tab's Variables over

    //View Tab's Variables start
    public boolean isChkColumns()
    {
        return chkColumns;
    }

    public void setChkColumns(final boolean chkColumns)
    {
        this.chkColumns = chkColumns;
    }

    public boolean isChkOnScreen()
    {
        return chkOnScreen;
    }

    public void setChkOnScreen(final boolean chkOnScreen)
    {
        this.chkOnScreen = chkOnScreen;
    }

    public boolean isChkPdf()
    {
        return chkPdf;
    }

    public void setChkPdf(final boolean chkPdf)
    {
        this.chkPdf = chkPdf;
    }

    public boolean isChkXls()
    {
        return chkXls;
    }

    public void setChkXls(final boolean chkXls)
    {
        this.chkXls = chkXls;
    }

    public String[] getHdnViewField()
    {
        return hdnViewField;
    }

    public void setHdnViewField(final String[] hdnViewField)
    {
        this.hdnViewField = hdnViewField;
    }

    public String[] getHdnViewControl()
    {
        return hdnViewControl;
    }

    public void setHdnViewControl(final String[] hdnViewControl)
    {
        this.hdnViewControl = hdnViewControl;
    }

    public String getCmbViewControlPos()
    {
        return cmbViewControlPos;
    }

    public void setCmbViewControlPos(final String cmbViewControlPos)
    {
        this.cmbViewControlPos = cmbViewControlPos;
    }

    public String[] getHdnViewTabIndex()
    {
        return hdnViewTabIndex;
    }

    public void setHdnViewTabIndex(final String[] hdnViewTabIndex)
    {
        this.hdnViewTabIndex = hdnViewTabIndex;
    }

    public String[] getHdncmbViewAlign()
    {
        return hdncmbViewAlign;
    }

    public void setHdncmbViewAlign(final String[] hdncmbViewAlign)
    {
        this.hdncmbViewAlign = hdncmbViewAlign;
    }

    public String[] getHdncmbViewValidation()
    {
        return hdncmbViewValidation;
    }

    public void setHdncmbViewValidation(final String[] hdncmbViewValidation)
    {
        this.hdncmbViewValidation = hdncmbViewValidation;
    }

    public String[] getHdnrbtnViewChecked()
    {
        return hdnrbtnViewChecked;
    }

    public void setHdnrbtnViewChecked(final String[] hdnrbtnViewChecked)
    {
        this.hdnrbtnViewChecked = hdnrbtnViewChecked;
    }

    public String[] getHdnrbtnViewMultiple()
    {
        return hdnrbtnViewMultiple;
    }

    public void setHdnrbtnViewMultiple(final String[] hdnrbtnViewMultiple)
    {
        this.hdnrbtnViewMultiple = hdnrbtnViewMultiple;
    }

    public String[] getHdnrbtnViewReadonly()
    {
        return hdnrbtnViewReadonly;
    }

    public void setHdnrbtnViewReadonly(final String[] hdnrbtnViewReadonly)
    {
        this.hdnrbtnViewReadonly = hdnrbtnViewReadonly;
    }

    public String[] getHdntxtViewClass()
    {
        return hdntxtViewClass;
    }

    public void setHdntxtViewClass(final String[] hdntxtViewClass)
    {
        this.hdntxtViewClass = hdntxtViewClass;
    }

    public String[] getHdncmbViewOnchange()
    {
        return hdncmbViewOnchange;
    }

    public void setHdncmbViewOnchange(final String[] hdncmbViewOnchange)
    {
        this.hdncmbViewOnchange = hdncmbViewOnchange;
    }

    public String[] getHdntxtViewCols()
    {
        return hdntxtViewCols;
    }

    public void setHdntxtViewCols(final String[] hdntxtViewCols)
    {
        this.hdntxtViewCols = hdntxtViewCols;
    }

    public String[] getHdntxtViewId()
    {
        return hdntxtViewId;
    }

    public void setHdntxtViewId(final String[] hdntxtViewId)
    {
        this.hdntxtViewId = hdntxtViewId;
    }

    public String[] getHdntxtViewMaxLength()
    {
        return hdntxtViewMaxLength;
    }

    public void setHdntxtViewMaxLength(final String[] hdntxtViewMaxLength)
    {
        this.hdntxtViewMaxLength = hdntxtViewMaxLength;
    }

    public String[] getHdntxtViewName()
    {
        return hdntxtViewName;
    }

    public void setHdntxtViewName(final String[] hdntxtViewName)
    {
        this.hdntxtViewName = hdntxtViewName;
    }

    public String[] getHdntxtViewRemarks()
    {
        return hdntxtViewRemarks;
    }

    public void setHdntxtViewRemarks(final String[] hdntxtViewRemarks)
    {
        this.hdntxtViewRemarks = hdntxtViewRemarks;
    }

    public String[] getHdntxtViewRows()
    {
        return hdntxtViewRows;
    }

    public void setHdntxtViewRows(final String[] hdntxtViewRows)
    {
        this.hdntxtViewRows = hdntxtViewRows;
    }

    public String[] getHdntxtViewSize()
    {
        return hdntxtViewSize;
    }

    public void setHdntxtViewSize(final String[] hdntxtViewSize)
    {
        this.hdntxtViewSize = hdntxtViewSize;
    }

    public String[] getHdntxtViewStyle()
    {
        return hdntxtViewStyle;
    }

    public void setHdntxtViewStyle(final String[] hdntxtViewStyle)
    {
        this.hdntxtViewStyle = hdntxtViewStyle;
    }

    public String[] getHdntxtViewValue()
    {
        return hdntxtViewValue;
    }

    public void setHdntxtViewValue(final String[] hdntxtViewValue)
    {
        this.hdntxtViewValue = hdntxtViewValue;
    }

    public String[] getHdnrbtnViewValueFormat()
    {
        return hdnrbtnViewValueFormat;
    }

    public void setHdnrbtnViewValueFormat(final String[] hdnrbtnViewValueFormat)
    {
        this.hdnrbtnViewValueFormat = hdnrbtnViewValueFormat;
    }

    public String[] getHdntxtViewRdoCap()
    {
        return hdntxtViewRdoCap;
    }

    public void setHdntxtViewRdoCap(final String[] hdntxtViewRdoCap)
    {
        this.hdntxtViewRdoCap = hdntxtViewRdoCap;
    }

    public String[] getHdntxtViewRdoVal()
    {
        return hdntxtViewRdoVal;
    }

    public void setHdntxtViewRdoVal(final String[] hdntxtViewRdoVal)
    {
        this.hdntxtViewRdoVal = hdntxtViewRdoVal;
    }

    public String[] getHdntxtViewRdoDefVal()
    {
        return hdntxtViewRdoDefVal;
    }

    public void setHdntxtViewRdoDefVal(final String[] hdntxtViewRdoDefVal)
    {
        this.hdntxtViewRdoDefVal = hdntxtViewRdoDefVal;
    }

    public String[] getHdntxtViewTotalRdo()
    {
        return hdntxtViewTotalRdo;
    }

    public void setHdntxtViewTotalRdo(final String[] hdntxtViewTotalRdo)
    {
        this.hdntxtViewTotalRdo = hdntxtViewTotalRdo;
    }

    public String[] getHdntxtViewLabel()
    {
        return hdntxtViewLabel;
    }

    public void setHdntxtViewLabel(final String[] hdntxtViewLabel)
    {
        this.hdntxtViewLabel = hdntxtViewLabel;
    }

    public String[] getHdnchkViewSrc()
    {
        return hdnchkViewSrc;
    }

    public void setHdnchkViewSrc(final String[] hdnchkViewSrc)
    {
        this.hdnchkViewSrc = hdnchkViewSrc;
    }

    public String[] getHdnrdoViewDataSrc()
    {
        return hdnrdoViewDataSrc;
    }

    public void setHdnrdoViewDataSrc(final String[] hdnrdoViewDataSrc)
    {
        this.hdnrdoViewDataSrc = hdnrdoViewDataSrc;
    }

    public String[] getHdntxtViewSrcStatic()
    {
        return hdntxtViewSrcStatic;
    }

    public void setHdntxtViewSrcStatic(final String[] hdntxtViewSrcStatic)
    {
        this.hdntxtViewSrcStatic = hdntxtViewSrcStatic;
    }

    public String[] getHdntxtViewSrcQuery()
    {
        return hdntxtViewSrcQuery;
    }

    public void setHdntxtViewSrcQuery(final String[] hdntxtViewSrcQuery)
    {
        this.hdntxtViewSrcQuery = hdntxtViewSrcQuery;
    }

    public String getTxtViewSrcQuery()
    {
        return txtViewSrcQuery;
    }

    public void setTxtViewSrcQuery(final String txtViewSrcQuery)
    {
        this.txtViewSrcQuery = txtViewSrcQuery;
    }

    public String[] getHdntxtViewWsdlUrl()
    {
        return hdntxtViewWsdlUrl;
    }

    public void setHdntxtViewWsdlUrl(final String[] hdntxtViewWsdlUrl)
    {
        this.hdntxtViewWsdlUrl = hdntxtViewWsdlUrl;
    }

    public String[] getHdncmbViewWsMethod()
    {
        return hdncmbViewWsMethod;
    }

    public void setHdncmbViewWsMethod(final String[] hdncmbViewWsMethod)
    {
        this.hdncmbViewWsMethod = hdncmbViewWsMethod;
    }

    public String[] getHdntxtViewWsCmbValue()
    {
        return hdntxtViewWsCmbValue;
    }

    public void setHdntxtViewWsCmbValue(final String[] hdntxtViewWsCmbValue)
    {
        this.hdntxtViewWsCmbValue = hdntxtViewWsCmbValue;
    }

    public String[] getHdntxtViewWsCmbText()
    {
        return hdntxtViewWsCmbText;
    }

    public void setHdntxtViewWsCmbText(final String[] hdntxtViewWsCmbText)
    {
        this.hdntxtViewWsCmbText = hdntxtViewWsCmbText;
    }

    public String[] getHdntxtViewWsProject()
    {
        return hdntxtViewWsProject;
    }

    public void setHdntxtViewWsProject(final String[] hdntxtViewWsProject)
    {
        this.hdntxtViewWsProject = hdntxtViewWsProject;
    }

    public String[] getHdntxtViewWsIntrface()
    {
        return hdntxtViewWsIntrface;
    }

    public void setHdntxtViewWsIntrface(final String[] hdntxtViewWsIntrface)
    {
        this.hdntxtViewWsIntrface = hdntxtViewWsIntrface;
    }

    public String[] getHdntxtViewWsRetType()
    {
        return hdntxtViewWsRetType;
    }

    public void setHdntxtViewWsRetType(final String[] hdntxtViewWsRetType)
    {
        this.hdntxtViewWsRetType = hdntxtViewWsRetType;
    }

    public String[] getHdntxtViewWsParams()
    {
        return hdntxtViewWsParams;
    }

    public void setHdntxtViewWsParams(final String[] hdntxtViewWsParams)
    {
        this.hdntxtViewWsParams = hdntxtViewWsParams;
    }

    public String[] getHdntxtViewWsExps()
    {
        return hdntxtViewWsExps;
    }

    public void setHdntxtViewWsExps(final String[] hdntxtViewWsExps)
    {
        this.hdntxtViewWsExps = hdntxtViewWsExps;
    }

    public String getTxtViewWsdlUrl()
    {
        return txtViewWsdlUrl;
    }

    public void setTxtViewWsdlUrl(final String txtViewWsdlUrl)
    {
        this.txtViewWsdlUrl = txtViewWsdlUrl;
    }

    public String getCmbViewWsMethod()
    {
        return cmbViewWsMethod;
    }

    public void setCmbViewWsMethod(final String cmbViewWsMethod)
    {
        this.cmbViewWsMethod = cmbViewWsMethod;
    }

    public String getTxtViewWsProject()
    {
        return txtViewWsProject;
    }

    public void setTxtViewWsProject(final String txtViewWsProject)
    {
        this.txtViewWsProject = txtViewWsProject;
    }

    public String getTxtViewWsIntrface()
    {
        return txtViewWsIntrface;
    }

    public void setTxtViewWsIntrface(final String txtViewWsIntrface)
    {
        this.txtViewWsIntrface = txtViewWsIntrface;
    }

    public String[] getHdnchkViewMandatory()
    {
        return hdnchkViewMandatory;
    }

    public void setHdnchkViewMandatory(final String[] hdnchkViewMandatory)
    {
        this.hdnchkViewMandatory = hdnchkViewMandatory;
    }

    public String[] getHdncmbViewCommonQuery()
    {
        return hdncmbViewCommonQuery;
    }

    public void setHdncmbViewCommonQuery(final String[] hdncmbViewCommonQuery)
    {
        this.hdncmbViewCommonQuery = hdncmbViewCommonQuery;
    }

    public String[] getHdntxtViewMaxsize()
    {
        return hdntxtViewMaxsize;
    }

    public void setHdntxtViewMaxsize(final String[] hdntxtViewMaxsize)
    {
        this.hdntxtViewMaxsize = hdntxtViewMaxsize;
    }

    public String[] getHdntxtViewType()
    {
        return hdntxtViewType;
    }

    public void setHdntxtViewType(final String[] hdntxtViewType)
    {
        this.hdntxtViewType = hdntxtViewType;
    }

    public String[] getHdntxtViewMaxfiles()
    {
        return hdntxtViewMaxfiles;
    }

    public void setHdntxtViewMaxfiles(final String[] hdntxtViewMaxfiles)
    {
        this.hdntxtViewMaxfiles = hdntxtViewMaxfiles;
    }

    public String[] getHdntxtViewEleName()
    {
        return hdntxtViewEleName;
    }

    public void setHdntxtViewEleName(final String[] hdntxtViewEleName)
    {
        this.hdntxtViewEleName = hdntxtViewEleName;
    }

    public String[] getHdntxtViewDispTxt()
    {
        return hdntxtViewDispTxt;
    }

    public void setHdntxtViewDispTxt(final String[] hdntxtViewDispTxt)
    {
        this.hdntxtViewDispTxt = hdntxtViewDispTxt;
    }

    public String[] getHdntxtViewOnremoveCall()
    {
        return hdntxtViewOnremoveCall;
    }

    public void setHdntxtViewOnremoveCall(final String[] hdntxtViewOnremoveCall)
    {
        this.hdntxtViewOnremoveCall = hdntxtViewOnremoveCall;
    }
    //View Tab's Variables over

    public int getSrNo()
    {
        return SrNo;
    }

    public void setSrNo(final int SrNo)
    {
        this.SrNo = SrNo;
    }

    public boolean isSchemaisalias()
    {
        return schemaisalias;
    }

    public void setSchemaisalias(final boolean schemaisalias)
    {
        this.schemaisalias = schemaisalias;
    }

    public String[] getHdnMstAllColumns()
    {
        return hdnMstAllColumns;
    }

    public void setHdnMstAllColumns(final String[] hdnMstAllColumns)
    {
        this.hdnMstAllColumns = hdnMstAllColumns;
    }

    public String[] getHdnMstAllDataTypes()
    {
        return hdnMstAllDataTypes;
    }

    public void setHdnMstAllDataTypes(final String[] hdnMstAllDataTypes)
    {
        this.hdnMstAllDataTypes = hdnMstAllDataTypes;
    }
}
