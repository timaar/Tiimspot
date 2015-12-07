'use strict';

describe('Ouder Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockOuder, MockPersoon, MockComment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockOuder = jasmine.createSpy('MockOuder');
        MockPersoon = jasmine.createSpy('MockPersoon');
        MockComment = jasmine.createSpy('MockComment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Ouder': MockOuder,
            'Persoon': MockPersoon,
            'Comment': MockComment
        };
        createController = function() {
            $injector.get('$controller')("OuderDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'tiimspotApp:ouderUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
