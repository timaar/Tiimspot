'use strict';

describe('Persoon Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPersoon, MockAdres, MockComment, MockOuder;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPersoon = jasmine.createSpy('MockPersoon');
        MockAdres = jasmine.createSpy('MockAdres');
        MockComment = jasmine.createSpy('MockComment');
        MockOuder = jasmine.createSpy('MockOuder');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Persoon': MockPersoon,
            'Adres': MockAdres,
            'Comment': MockComment,
            'Ouder': MockOuder
        };
        createController = function() {
            $injector.get('$controller')("PersoonDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'tiimspotApp:persoonUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
