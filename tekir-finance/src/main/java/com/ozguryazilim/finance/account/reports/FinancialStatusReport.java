package com.ozguryazilim.finance.account.reports;


import com.ozguryazilim.finance.account.FinanceAccountTxnStatusModel;
import com.ozguryazilim.finance.account.txn.FinanceAccountTxnRepository;
import com.ozguryazilim.finance.config.FinancePages;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import com.ozguryazilim.tekir.entities.AccountType;
import com.ozguryazilim.telve.messages.Messages;
import com.ozguryazilim.telve.query.filters.DateValueType;
import com.ozguryazilim.telve.reports.DynamicReportBase;
import com.ozguryazilim.telve.reports.Report;
import com.ozguryazilim.telve.reports.ReportDate;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Report(filterPage = FinancePages.FinancialStatusReport.class, permission="financialStatusReport", path = "/finance/account", template = "financialStatusReport", resource = "financeReports")

public class FinancialStatusReport extends
		DynamicReportBase<FinancialStatusFilter> {

	private static final Logger LOG = LoggerFactory.getLogger(FinancialStatusReport.class);

	@Inject
	private FinanceAccountTxnRepository repository;

	@Override
	public FinancialStatusFilter buildFilter() {
		FinancialStatusFilter result = new FinancialStatusFilter();

		result.setDate(new ReportDate(DateValueType.Today));

		return result;
	}

	@Override
	protected void buildReport(JasperReportBuilder report, Boolean forExport) {

		TextColumnBuilder<String> accountName = col.column( "accountName",
				type.stringType())
				.setTitle(msg("FinancialStatusReport" +
						".Name"));
		TextColumnBuilder<String> code = col.column( "code", type
				.stringType())
				.setTitle(msg("FinancialStatusReport" +
						".Code"))
				.setFixedWidth(cm(3));
		TextColumnBuilder<BigDecimal> debit = col.column( "debit", type.bigDecimalType())
				.setTitle(msg("FinancialStatusReport.Debit"))
				.setFixedWidth(cm(3));
		TextColumnBuilder<BigDecimal> credit = col.column( "credit", type.bigDecimalType())
				.setTitle(msg("FinancialStatusReport.Credit"))
				.setFixedWidth(cm(3));
		TextColumnBuilder<BigDecimal> balance = col.column( "balance", type.bigDecimalType())
				.setTitle(msg("FinancialStatusReport.Balance"))
				.setFixedWidth(cm(3));

		report.columns( accountName, code, debit, credit, balance );

		if( !forExport ){
			report.subtotalsAtSummary(
					sbt.text(Messages.getMessage("general.label.Total"), accountName),
					sbt.sum(debit),
					sbt.sum(credit),
					sbt.sum(balance)
			);
		}

	}

	public List<AccountType> getAccountTypes() {
		return Arrays.asList(AccountType.values());
	}

	@Override
	protected String getReportSubTitle() {
		String pattern = msg("general.format.Date");
		DateFormat df = new SimpleDateFormat(pattern);
		Date dt = getFilter().getDate().getCalculatedValue();

		StringBuilder sb = new StringBuilder();
		sb.append(msg("FinancialStatusReport.asOfDate")).append(" : ").append
				(df.format(dt))
				.append('\n');
		if(getFilter().getFinanceAccount() != null){
			sb.append(msg("FinancialStatusReport.Name")).append(" : ").append
					(getFilter()
					.getFinanceAccount()
					.getName())
					.append('\n');
		}

		return sb.toString();
	}



	@Override
	protected JRDataSource getReportDataSource() {
		List<FinanceAccountTxnStatusModel> rows = repository.findAccountStatus
				(getFilter());
		return new JRBeanCollectionDataSource(rows);
	}

}