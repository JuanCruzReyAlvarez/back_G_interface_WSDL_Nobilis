package laboratorio.repository.templates;

import laboratorio.dao.templates.TemplateDao;
import laboratorio.entities.template.Formato;
import laboratorio.entities.template.Template;

public class TemplateRepository {
    private TemplateDao dao;

    public TemplateRepository() {
        this.dao = TemplateDao.getInstance();
        this.dao.setClazz(Template.class);
    } 

    public Template save(Template paciente) {
        return this.dao.save(paciente);
    }

    public void update(Template resultado) {
        this.dao.update(resultado);
    }

    public Template getFormat(Formato formato) {
        return this.dao.getFormat(formato);
    }
    
}
