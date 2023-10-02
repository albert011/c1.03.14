
package acme.features.auditor.auditorDashboard;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.lecture.LectureType;
import acme.forms.AuditorDashboard;
import acme.forms.UtilsMath;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final AuditorDashboard dashboard;
		final Auditor auditor;
		final int auditorId;
		Collection<Audit> audits;
		Collection<AuditRecord> auditRecords;

		int numberOfAuditsHandsOn, numberOfAuditsTheory;
		double averageNumberAuditRecords, deviationNumberAuditRecords;
		int minNumberAuditRecords, maxNumberAuditRecords;

		double averagePeriodLengths, deviationPeriodLengths;
		double minPeriodLengths, maxPeriodLengths;

		auditorId = super.getRequest().getPrincipal().getAccountId();
		auditor = this.repository.getAuditorById(auditorId);
		audits = this.repository.getAudits(auditor);
		auditRecords = this.repository.getAuditRecords(auditor);

		final Map<Audit, Long> numberOfAuditRecords = auditRecords.stream().collect(Collectors.groupingBy(AuditRecord::getAudit, Collectors.counting()));

		final List<Double> numbersAuditRecords = numberOfAuditRecords.values().stream().map(x -> (double) x).collect(Collectors.toList());
		final List<Double> timesAuditRecordsSeconds = auditRecords.stream().mapToDouble(x -> (x.getPeriodEnd().getTime() - x.getPeriodStart().getTime()) / (60 * 60 * 1000)).boxed().collect(Collectors.toList());

		numberOfAuditsHandsOn = Integer.max(0, (int) audits.stream().filter(x -> x.getCourse().getType().equals(LectureType.HANDS_ON)).count());
		numberOfAuditsTheory = Integer.max(0, (int) audits.stream().filter(x -> x.getCourse().getType().equals(LectureType.THEORETICAL)).count());

		averageNumberAuditRecords = UtilsMath.getAverage(numbersAuditRecords);
		deviationNumberAuditRecords = UtilsMath.getDeviation(numbersAuditRecords);
		minNumberAuditRecords = UtilsMath.getMinimum(numbersAuditRecords).intValue();
		maxNumberAuditRecords = UtilsMath.getMaximum(numbersAuditRecords).intValue();

		averagePeriodLengths = UtilsMath.getAverage(timesAuditRecordsSeconds);
		deviationPeriodLengths = UtilsMath.getDeviation(timesAuditRecordsSeconds);
		minPeriodLengths = UtilsMath.getMinimum(timesAuditRecordsSeconds);
		maxPeriodLengths = UtilsMath.getMaximum(timesAuditRecordsSeconds);

		dashboard = new AuditorDashboard();
		dashboard.setTotalAuditsHandOn(numberOfAuditsHandsOn);
		dashboard.setTotalAuditsTheory(numberOfAuditsTheory);

		dashboard.setAverageAuditRecords(averageNumberAuditRecords);
		dashboard.setStandardDeviationAuditRecords(deviationNumberAuditRecords);
		dashboard.setMinimumAuditRecords(minNumberAuditRecords);
		dashboard.setMaximumAuditRecords(maxNumberAuditRecords);

		dashboard.setAveragePeriodLengthAuditRecords(averagePeriodLengths);
		dashboard.setStandardPeriodLengthDeviationAuditRecords(deviationPeriodLengths);
		dashboard.setMinimumPeriodLengthAuditRecords(minPeriodLengths);
		dashboard.setMaximumPeriodLengthAuditRecords(maxPeriodLengths);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		double minPeriodLength, maxPeriodLength, avgPeriodLength, stdPeriodLength;

		minPeriodLength = object.getMinimumPeriodLengthAuditRecords();
		maxPeriodLength = object.getMaximumPeriodLengthAuditRecords();

		avgPeriodLength = object.getAveragePeriodLengthAuditRecords();
		stdPeriodLength = object.getStandardPeriodLengthDeviationAuditRecords();

		double minPeriodLengthS, maxPeriodLengthS, stdPeriodLengthS, avgPeriodLengthS;
		int minPeriodLengthH, minPeriodLengthM;
		int maxPeriodLengthH, maxPeriodLengthM;
		int avgPeriodLengthH, avgPeriodLengthM;
		int stdPeriodLengthH, stdPeriodLengthM;

		minPeriodLengthH = (int) minPeriodLength / 3600;
		minPeriodLengthM = (int) (minPeriodLength - 60 * minPeriodLengthH) / 60;
		minPeriodLengthS = minPeriodLength - minPeriodLengthH * 3600 - minPeriodLengthM * 60;

		maxPeriodLengthH = (int) maxPeriodLength / 3600;
		maxPeriodLengthM = (int) (maxPeriodLength - 60 * maxPeriodLengthH) / 60;
		maxPeriodLengthS = maxPeriodLength - maxPeriodLengthH * 3600 - maxPeriodLengthM * 60;

		avgPeriodLengthH = (int) avgPeriodLength / 3600;
		avgPeriodLengthM = (int) (avgPeriodLength - 60 * avgPeriodLengthH) / 60;
		avgPeriodLengthS = avgPeriodLength - avgPeriodLengthH * 3600 - avgPeriodLengthM * 60;

		stdPeriodLengthH = (int) stdPeriodLength / 3600;
		stdPeriodLengthM = (int) (minPeriodLength - 60 * stdPeriodLengthH) / 60;
		stdPeriodLengthS = stdPeriodLength - stdPeriodLengthH * 3600 - stdPeriodLengthM * 60;

		tuple = super.unbind(object, "totalAuditsTheory", "totalAuditsHandOn",//
			"minimumAuditRecords", "maximumAuditRecords",//
			"averageAuditRecords", "standardDeviationAuditRecords");

		tuple.put("minPeriodLengthH", minPeriodLengthH);
		tuple.put("minPeriodLengthM", minPeriodLengthM);
		tuple.put("minPeriodLengthS", minPeriodLengthS);

		tuple.put("maxPeriodLengthH", maxPeriodLengthH);
		tuple.put("maxPeriodLengthM", maxPeriodLengthM);
		tuple.put("maxPeriodLengthS", maxPeriodLengthS);

		tuple.put("avgPeriodLengthH", avgPeriodLengthH);
		tuple.put("avgPeriodLengthM", avgPeriodLengthM);
		tuple.put("avgPeriodLengthS", avgPeriodLengthS);

		tuple.put("stdPeriodLengthH", stdPeriodLengthH);
		tuple.put("stdPeriodLengthM", stdPeriodLengthM);
		tuple.put("stdPeriodLengthS", stdPeriodLengthS);

		super.getResponse().setData(tuple);
	}
}
