
package acme.features.student.enrolment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		final Principal principal;
		final int masterId;

		masterId = super.getRequest().getData("id", int.class);
		principal = super.getRequest().getPrincipal();

		status = this.repository.findOneEnrolmentById(masterId).getStudent().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;
		object.setHolderName(this.getRequest().getData("holderName", String.class));
		super.bind(object, "holderName", "lowerNibble");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		SimpleDateFormat format;
		final Date moment = MomentHelper.getCurrentMoment();
		final String res = this.getRequest().getData("expiryDate", String.class);
		String month = "", year = "";

		if (super.getRequest().getLocale().getLanguage().equals("es"))
			format = new SimpleDateFormat("MM/YY");
		else
			format = new SimpleDateFormat("YY/MM");

		final Calendar calendar = Calendar.getInstance();
		Date fechaFinal = new Date();
		if (res.length() == 5 && res.substring(2, 3).equals("/") && res.substring(0, 2).matches("\\d+") && res.substring(res.length() - 2).matches("\\d+")) {

			if (super.getRequest().getLocale().getLanguage().equals("es")) {
				month = res.substring(0, 2);
				year = res.substring(res.length() - 2);
				if (year.substring(0, 1).equals("/"))
					year = res.substring(res.length() - 1);
			} else {
				year = res.substring(0, 2);
				month = res.substring(res.length() - 2);
				if (month.substring(0, 1).equals("/"))
					month = res.substring(res.length() - 1);
			}
			calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
			calendar.set(Calendar.YEAR, Integer.parseInt("20" + year));
			// Obtener el último día del mes
			final int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, lastDay);

			// Establecer la hora a las 23:59
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);

			fechaFinal = calendar.getTime();
		}

		String creditCard = super.getRequest().getData("creditCard", String.class);
		creditCard = creditCard.replaceAll("\\D", "");
		if (!StudentEnrolmentFinaliseService.validateCreditCard(creditCard) || !creditCard.matches("\\d+") || creditCard.isEmpty() || creditCard.length() != 16)
			super.state(false, "creditCard", "student.enrolment.form.error.card");

		final String holderName = super.getRequest().getData("holderName", String.class);
		if (!super.getBuffer().getErrors().hasErrors("holderName"))
			super.state(!holderName.isEmpty(), "holderName", "student.enrolment.form.error.holder");
		final String cvc = super.getRequest().getData("cvc", String.class);
		if (!super.getBuffer().getErrors().hasErrors("cvc"))
			super.state(cvc.matches("^\\d{3}$"), "cvc", "student.enrolment.form.error.cvc");
		final String expiryDate = super.getRequest().getData("expiryDate", String.class);
		if (!super.getBuffer().getErrors().hasErrors("expiryDate"))
			if (res.length() == 5 && res.substring(2, 3).equals("/") && res.substring(0, 2).matches("\\d+") && res.substring(res.length() - 2).matches("\\d+")) {
				super.state(Integer.parseInt(month) <= 12 && Integer.parseInt(month) != 00, "expiryDate", "student.enrolment.form.error.month");
				super.state(!MomentHelper.isAfterOrEqual(moment, fechaFinal), "expiryDate", "student.enrolment.form.error.limit");
			}
		super.state(expiryDate.matches("^\\d{2}\\/\\d{2}$"), "expiryDate", "student.enrolment.form.error.expiryDate");
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		object.setLowerNibble(this.getRequest().getData("creditCard", String.class).substring(this.getRequest().getData("creditCard", String.class).length() - 4));
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;
		boolean finalized = false;

		final String creditCard = this.getRequest().getData("creditCard", String.class);
		final String expiryDate = this.getRequest().getData("expiryDate", String.class);
		final String cvc = this.getRequest().getData("cvc", String.class);

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());
		final List<Activity> list = this.repository.findManyActivitiesById(object.getId());

		if (object.getHolderName() != null && !object.getHolderName().isEmpty() && object.getLowerNibble() != null && !object.getLowerNibble().isEmpty())
			finalized = true;

		tuple = super.unbind(object, "code", "motivation", "goals", "holderName", "lowerNibble");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("cvc", cvc);
		tuple.put("creditCard", creditCard);
		tuple.put("expiryDate", expiryDate);
		tuple.put("workTime", object.workTime(list));
		tuple.put("finalized", finalized);
		super.getResponse().setData(tuple);
	}

	//Metodos auxiliares ----------------------------------------------------
	private static boolean validateCreditCard(final String creditCard) {
		int sum = 0;
		boolean doubleDigit = false;

		// Iterar sobre los dígitos de derecha a izquierda
		for (int i = creditCard.length() - 1; i >= 0; i--) {
			int digit = Integer.parseInt(creditCard.substring(i, i + 1));

			if (doubleDigit) {
				digit *= 2;
				if (digit > 9)
					digit = digit % 10 + 1;
			}

			sum += digit;
			doubleDigit = !doubleDigit;
		}

		// La suma total debe ser divisible por 10 para que el número sea válido
		return sum % 10 == 0;
	}

}
