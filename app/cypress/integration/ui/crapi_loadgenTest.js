///<reference types="Cypress"/>
var i = 0;
var j = 5;
describe('E2E Testing of crAPI', function()
{
    it('E2E Testing',function()
    {
        cy.clearCookies();
        cy.visit(Cypress.env('site'));
       // cy.visit("http://crapi.levo.ai")
        cy.get("#basic_email").type("hacker@darkweb.com");
        cy.get("#basic_password").type("Hack3r$$$");
        cy.get("button[type='submit']").click();
        cy.log("Login Successfull");

        for (i = 0; i < j ; i++) {  
        cy.get('[style="opacity: 1; order: 0;"] > .ant-menu-title-content').click();
        cy.get('span').contains('Contact Mechanic').click();
        cy.get("#add-vehicle_mechanicCode").click();
        const resizeObserverLoopErrRe = /^[^(ResizeObserver loop limit exceeded)]/
        Cypress.on('uncaught:exception', (err) => {
         /* returning false here prevents Cypress from failing the test */
         if (resizeObserverLoopErrRe.test(err.message)) {
          return false
         }
        })
        cy.get("div[title='TRAC_MECH1']").click();
        cy.get("#add-vehicle_problemDetails").click().type("Test");
        cy.get("[type='submit']").click();
        cy.get('.ant-modal-confirm-btns > .ant-btn > span').click();
        cy.get('span').contains('View Service Reports').click();
        cy.wait(15000)
        cy.get('span').contains('Back to Dashboard').should('be.visible').click({force:true});
        cy.log("Dashboard action Successfull");

        cy.get('[style="opacity: 1; order: 1;"] > .ant-menu-title-content').click();
        // cy.get(':nth-child(1) > .ant-card > .ant-card-body > .ant-card-meta > .ant-card-meta-detail > .ant-card-meta-description > .ant-btn').should('be.visible').click();
        // cy.get('.ant-modal-confirm-btns > .ant-btn > span').should('be.visible').click();
        // cy.get('.ant-btn > :nth-child(2)').should('be.visible').click();
        // cy.get('.ant-modal-confirm-btns > .ant-btn > span').should('be.visible').click();
        cy.log("Shop Action Successfull");

        cy.get('[style="opacity: 1; order: 2;"] > .ant-menu-title-content').click();
        cy.get('span').contains('New Post').click();
        cy.get("#new-post_title").click().type("Test");
        cy.get("#new-post_content").click().type("Test");
        cy.get('span').contains('Add New Post').click();
        cy.get('.ant-modal-confirm-btns > .ant-btn > span').click();
        cy.get(':nth-child(1) > .ant-card > .ant-card-body').click();
        cy.get('span').contains('Add Comment').click();
        cy.get("#basic_comment").click().type("Test1");
        cy.get('span').contains('Add a Comment').click();
        cy.get('.ant-modal-confirm-btns > .ant-btn > span').click();
        cy.log("Community Action Successfull");
    }

        cy.get("div.ant-dropdown-trigger.nav-items").click();
        cy.get("li.ant-dropdown-menu-item:nth-child(2)").click();
        cy.log("Logout Action Successfull");



    })
})
